package com.hospital.controller;

import com.hospital.dto.ApiResponse;
import com.hospital.dto.DoctorCreateRequest;
import com.hospital.dto.DoctorUpdateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	@Autowired
    private DoctorService doctorService;

    // CREATE: Yeni doktor ekle
    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(
            @Valid @RequestBody DoctorCreateRequest request) {
        
        DoctorResponse response = doctorService.createDoctor(request);
        
        return ResponseEntity.ok(ApiResponse.<DoctorResponse>builder()
                .success(true)
                .data(response)
                .message("Doktor kaydı başarıyla oluşturuldu.")
                .build());
    }

    // READ: Tüm doktorları listele
    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAllDoctors() {
        
        List<DoctorResponse> doctors = doctorService.getAllDoctors();
        
        return ResponseEntity.ok(ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .data(doctors)
                .build());
    }

    // READ: ID'ye göre tek bir doktoru getir
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable Long id) {
        
        DoctorResponse response = doctorService.getDoctorById(id);
        
        return ResponseEntity.ok(ApiResponse.<DoctorResponse>builder()
                .success(true)
                .data(response)
                .build());
    }

    // UPDATE: Mevcut doktor bilgilerini güncelle
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorUpdateRequest request) {
        
        DoctorResponse response = doctorService.updateDoctor(id, request);
        
        return ResponseEntity.ok(ApiResponse.<DoctorResponse>builder()
                .success(true)
                .data(response)
                .message("Doktor bilgileri başarıyla güncellendi.")
                .build());
    }



    // CUSTOM: Belirli bir tarihteki müsait (boş) saatleri getir
    @GetMapping("/{id}/available-slots")
    public ResponseEntity<ApiResponse<List<LocalTime>>> getAvailableSlots(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        List<LocalTime> availableSlots = doctorService.getAvailableSlots(id, date);
        
        return ResponseEntity.ok(ApiResponse.<List<LocalTime>>builder()
                .success(true)
                .data(availableSlots)
                .build());
    }
}