package com.hospital.service;

import com.hospital.dto.DoctorCreateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.dto.DoctorUpdateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.entity.*;
import com.hospital.enums.Role;
import com.hospital.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // --- CRUD İŞLEMLERİ ---

    @Transactional
    public DoctorResponse createDoctor(DoctorCreateRequest dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Departman bulunamadı!"));

        Doctor doctor = new Doctor();
        doctor.setAlias(dto.getAlias());
        doctor.setTitle(dto.getTitle());
        doctor.setDepartment(department);

        // --- BURASI EKLENECEK ---
        Users newUser = new Users();
        // Not: DTO'nda bu bilgilerin (ad, soyad, email, şifre vb.) olduğunu varsayıyoruz.
        newUser.setFirstname(dto.getFirstname()); 
        newUser.setLastname(dto.getLastname());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(dto.getPassword()); // Şifreyi gerçek senaryoda BCrypt ile şifrelemelisin
        newUser.setRole(Role.ROLE_DOCTOR); // Rolü otomatik olarak DOCTOR atıyoruz
        newUser.setActive(true);
        
        doctor.setUserAccount(newUser); // CascadeType.ALL sayesinde doctor kaydedilince user da kaydedilecek
        // ------------------------

        Doctor savedDoctor = doctorRepository.save(doctor);
        
        // Doktor kaydedildikten hemen sonra standart çalışma takvimini oluştur
        createDefaultWeeklySchedule(savedDoctor);

        return mapToResponseDTO(savedDoctor);
    }
    private void createDefaultWeeklySchedule(Doctor doctor) {
        List<DayOfWeek> workingDays = List.of(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
        );

        List<DoctorSchedule> schedules = new ArrayList<>();
        for (DayOfWeek day : workingDays) {
            DoctorSchedule schedule = new DoctorSchedule();
            schedule.setDoctor(doctor);
            schedule.setDayOfWeek(day);
            schedule.setStartTime(LocalTime.of(9, 0));  // Varsayılan mesai başlangıcı
            schedule.setEndTime(LocalTime.of(17, 0)); // Varsayılan mesai bitişi
            schedule.setSlotDuration(30);             // Varsayılan randevu süresi (dk)
            schedules.add(schedule);
        }
        
        scheduleRepository.saveAll(schedules);
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı! ID: " + id));
        return mapToResponseDTO(doctor);
    }

    @Transactional
    public DoctorResponse updateDoctor(Long id, DoctorUpdateRequest dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı! ID: " + id));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Departman bulunamadı!"));

        doctor.setAlias(dto.getAlias());
        doctor.setTitle(dto.getTitle());
        doctor.setDepartment(department);

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return mapToResponseDTO(updatedDoctor);
    }

    

    // --- RANDEVU MANTIĞI İŞLEMLERİ ---

    /**
     * Doktorun belirtilen tarihteki müsait (alınmamış) saat dilimlerini döndürür.
     */
    public List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // 1. Doktorun o günkü çalışma takvimini getir
        DoctorSchedule schedule = scheduleRepository.findByDoctorIdAndDayOfWeek(doctorId, dayOfWeek)
                .orElseThrow(() -> new RuntimeException("Doktorun bu tarihte mesaisi bulunmamaktadır."));

        // 2. Doktorun o günkü alınmış (dolu) randevularını getir
        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
        
        // Sadece dolu olan saatleri bir listeye çıkarıyoruz
        List<LocalTime> bookedTimes = bookedAppointments.stream()
                .map(Appointment::getAppointmentTime) // Appointment entity'nde appointmentTime (LocalTime) olduğunu varsayıyoruz
                .collect(Collectors.toList());

        // 3. Müsait saatleri hesapla
        List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime currentTime = schedule.getStartTime();
        LocalTime endTime = schedule.getEndTime();
        Integer slotDuration = schedule.getSlotDuration(); // Örn: 60 (1 saatte 1 hasta için)

        while (currentTime.plusMinutes(slotDuration).isBefore(endTime) || 
               currentTime.plusMinutes(slotDuration).equals(endTime)) {
            
            // Eğer o saat diliminde randevu yoksa müsait listesine ekle
            if (!bookedTimes.contains(currentTime)) {
                availableSlots.add(currentTime);
            }
            // Bir sonraki randevu dilimine geç
            currentTime = currentTime.plusMinutes(slotDuration);
        }

        return availableSlots;
    }

    
    private DoctorResponse mapToResponseDTO(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .alias(doctor.getAlias())
                .title(doctor.getTitle())
                .departmentName(doctor.getDepartment() != null ? doctor.getDepartment().getName() : null)
                .departmentId(doctor.getDepartment().getId())
                .build();
    }
}