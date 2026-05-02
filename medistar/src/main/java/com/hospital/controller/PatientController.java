package com.hospital.controller;


import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.ApiResponse;
import com.hospital.dto.PatientCreateRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.dto.PatientUpdateRequest;
import com.hospital.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
	
	@Autowired
	private PatientService patientService;

    // CREATE: Yeni hasta kaydı
    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(
            @Valid @RequestBody PatientCreateRequest request) {
        
        PatientResponse response = patientService.createPatient(request);
        
        return ResponseEntity.ok(ApiResponse.<PatientResponse>builder()
                .success(true)
                .data(response)
                .message("Hasta profili başarıyla oluşturuldu.")
                .build());
    }

    // READ: Tüm hastaları getir (Admin veya Doktor yetkisiyle kullanılabilir)
    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAllPatients() {
        
        List<PatientResponse> patients = patientService.getAllPatients();
        
        return ResponseEntity.ok(ApiResponse.<List<PatientResponse>>builder()
                .success(true)
                .data(patients)
                .build());
    }

    // READ: ID'ye göre tek bir hasta profilini getir
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(@PathVariable Long id) {
        
        PatientResponse response = patientService.getPatientById(id);
        
        return ResponseEntity.ok(ApiResponse.<PatientResponse>builder()
                .success(true)
                .data(response)
                .build());
    }

    // UPDATE: Mevcut hasta bilgilerini güncelle
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateRequest request) {
        
        PatientResponse response = patientService.updatePatient(id, request);
        
        return ResponseEntity.ok(ApiResponse.<PatientResponse>builder()
                .success(true)
                .data(response)
                .message("Hasta bilgileri başarıyla güncellendi.")
                .build());
    }

    // DELETE: Hasta kaydını sistemden sil
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        
        patientService.deletePatient(id);
        
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Hasta kaydı sistemden silindi.")
                .build());
    }
}