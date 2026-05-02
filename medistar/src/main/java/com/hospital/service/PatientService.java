package com.hospital.service;

import com.hospital.controller.AuthController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.Exception.BaseException;
import com.hospital.Exception.ErrorMessage;
import com.hospital.Exception.MessageType;
import com.hospital.dto.PatientCreateRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.dto.PatientUpdateRequest;
import com.hospital.entity.Patient;
import com.hospital.entity.Users;
import com.hospital.enums.PriorityStatus;
import com.hospital.enums.Role;
import com.hospital.mapper.PatientMapper;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;

@Service
public class PatientService {

	private final AuthController authController;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientMapper patientMapper;

	PatientService(AuthController authController) {
		this.authController = authController;
	}

	@Transactional
	public PatientResponse createPatient(PatientCreateRequest request) {
		// 1. TC No sistemde kayıtlı mı kontrolü
		if (patientRepository.existsByTcNo(request.getTcNo())) {
			throw new RuntimeException("Bu TC Kimlik numarası ile kayıtlı bir hasta zaten mevcut!");
		}

		// 2. DTO'yu Entity'e çevir
		Patient patient = patientMapper.toEntity(request);

		// --- DEĞİŞTİRİLECEK / EKLENECEK KISIM BURASI ---
		// 3. Yeni hasta için otomatik olarak bir User oluştur
		Users newUser = new Users();
		newUser.setFirstname(request.getFirstName());
		newUser.setLastname(request.getLastName());
		newUser.setEmail(request.getEmail());
		newUser.setTcNo(request.getTcNo());
		newUser.setPhone(request.getPhone());
		// Hasta kayıt olurken bir şifre belirlemelidir, bunu DTO'dan alıyoruz
		newUser.setPassword(request.getPassword()); // Gerçek senaryoda şifrelenmeli (PasswordEncoder vb.)
		newUser.setRole(Role.ROLE_PATIENT); // Rolü otomatik PATIENT atıyoruz
		newUser.setActive(true);

		patient.setUserAccount(newUser);
		// ----------------------------------------------

		// 4. Kaydet ve DTO olarak geri dön (toDto yerine toResponse kullanıyoruz)
		Patient savedPatient = patientRepository.save(patient);
		return patientMapper.toResponse(savedPatient);
	}

	@Transactional(readOnly = true)
	public PatientResponse getPatientById(Long id) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Hasta bulunamadı. ID: " + id));

		return patientMapper.toResponse(patient); // toDto yerine toResponse
	}

	public List<PatientResponse> getAllPatients() {
		List<Patient> patientList =patientRepository.findAll();
		List<PatientResponse> responseList = new ArrayList<>();
		
		
		for (Patient patientItem :patientList) {
			
			
			PatientResponse patientRes=new PatientResponse();
			
			patientRes.setId(patientItem.getId());
			patientRes.setFirstName(patientItem.getFirstName());
			patientRes.setLastName(patientItem.getLastName());
			patientRes.setEmail(patientItem.getEmail());
			patientRes.setPhone(patientItem.getPhone());
			patientRes.setPriorityStatus(patientItem.getPriorityStatus());
			patientRes.setUserId(patientItem.getUserAccount().getId());
			
			responseList.add(patientRes);
			
		}
		
		
		
		return responseList;
	}

	@Transactional
	public PatientResponse updatePatient(Long id, PatientUpdateRequest request) {
		// 1. Güncellenecek hastayı bul
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Güncellenecek hasta bulunamadı. ID: " + id));

		// 2. MapStruct ile DTO'daki dolu alanları mevcut Entity'nin üzerine yaz
		patientMapper.updateEntity(patient, request);

		// 3. Veritabanına kaydet
		Patient updatedPatient = patientRepository.save(patient);

		return patientMapper.toResponse(updatedPatient); // toDto yerine toResponse
	}

	@Transactional
	public void deletePatient(Long id) {
		if (!patientRepository.existsById(id)) {
			throw new RuntimeException("Silinecek hasta bulunamadı. ID: " + id);
		}
		patientRepository.deleteById(id);
	}
}