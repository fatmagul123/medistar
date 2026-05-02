package com.hospital.service;

import com.hospital.dto.AppointmentCreateRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.dto.AppointmentUpdateRequest;
import com.hospital.entity.*;
import com.hospital.enums.AppointmentStatus;
import com.hospital.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PseudoColumnUsage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private MailService emailService; // Mail gönderimi için eklendi

	@Transactional
	public AppointmentResponse createAppointment(AppointmentCreateRequest request, String currentUserEmail) {
		// 1. Mevcut hastayı email üzerinden bul (Sistemde giriş yapmış olan hasta)
		Patient patient = patientRepository.findByEmail(currentUserEmail)
				.orElseThrow(() -> new RuntimeException("Hasta profili bulunamadı!"));

		// 2. Doktoru bul
		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> new RuntimeException("Doktor bulunamadı!"));

		// 3. Çakışma Kontrolü (Aynı doktor, aynı tarih, aynı saat)
		boolean isSlotFull = appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(doctor.getId(),
				request.getAppointmentDate(), request.getAppointmentTime());

		if (isSlotFull) {
			throw new RuntimeException("Bu randevu saati dolu!");
		}

		// 4. Randevuyu oluştur
		Appointment appointment = new Appointment();
		appointment.setPatient(patient);
		appointment.setDoctor(doctor);
		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setAppointmentTime(request.getAppointmentTime());
		appointment.setPriority(request.getPriority());
		appointment.setStatus(AppointmentStatus.PENDING); // Varsayılan: Bekliyor

		Appointment savedAppointment = appointmentRepository.save(appointment);
		
		
		AppointmentResponse responseString =mapToResponseDTO(savedAppointment);
		responseString.setPatientId(patient.getId());
		responseString.setUserEmail(patient.getEmail());
		responseString.setDoctorId(doctor.getId());
		responseString.setDoctorName(doctor.getAlias());
		responseString.setNotes(appointment.getNotes());
		responseString.setPriority(request.getPriority());
		
		// 5. Mail Gönderimi (Mail adresi direkt patient nesnesinden alınıyor)
		/*System.out.println("Mail Gönderiliyor");
		emailService.sendSimpleMail(patient.getEmail(), savedAppointment);
		System.out.println("Mail gönderildi.");*/

		return responseString;
	}

	public List<AppointmentResponse> getUserAppointments(String email) {
		return appointmentRepository.findByPatientEmail(email).stream().map(this::mapToResponseDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public AppointmentResponse updateAppointmentStatus(Long id, AppointmentUpdateRequest request) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Randevu bulunamadı!"));

		appointment.setStatus(request.getStatus());
		return mapToResponseDTO(appointmentRepository.save(appointment));
	}

	@Transactional
	public void deleteAppointment(Long id) {
		if (!appointmentRepository.existsById(id)) {
			throw new RuntimeException("Silinecek randevu bulunamadı.");
		}
		appointmentRepository.deleteById(id);
	}

	public List<String> getBookedSlots(Long doctorId, LocalDate date) {
		return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date).stream()
				.map(app -> app.getAppointmentTime().toString()).collect(Collectors.toList());
	}

	private AppointmentResponse mapToResponseDTO(Appointment appointment) {
	    // 1. Önce DTO nesnesini oluştur
	    AppointmentResponse response = new AppointmentResponse();

	    // 2. Temel bilgileri ata (Bunlar genelde null gelmez ama açık yazmak takibi kolaylaştırır)
	    response.setId(appointment.getId());
	    response.setAppointmentDate(appointment.getAppointmentDate());
	    response.setAppointmentTime(appointment.getAppointmentTime());
	    response.setStatus(appointment.getStatus());

	    // 3. Hasta bilgilerini ata (Patient null kontrolü)
	    if (appointment.getPatient() != null) {
	        String patientFullName = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
	        response.setPatientFullName(patientFullName);
	    }

	    // 4. Doktor ve Departman bilgilerini ata (İç içe null kontrolü)
	    if (appointment.getDoctor() != null) {
	        // Doktor adı
	        String docName = appointment.getDoctor().getTitle() + " " + appointment.getDoctor().getAlias();
	        response.setDoctorName(docName);

	        // Departman adı (Doctor -> Department)
	        if (appointment.getDoctor().getDepartment() != null) {
	            response.setDeptName(appointment.getDoctor().getDepartment().getName());
	        } else {
	            response.setDeptName("Departman Bilgisi Yok");;
	        }
	    }

	    return response;
	}
}