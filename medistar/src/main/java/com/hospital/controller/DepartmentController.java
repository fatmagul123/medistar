package com.hospital.controller;

import com.hospital.dto.ApiResponse;
import com.hospital.dto.DepartmentCreateRequest;
import com.hospital.dto.DepartmentDetailResponse;
import com.hospital.dto.DepartmentResponse;
import com.hospital.dto.DepartmentUpdateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // --- STANDART CRUD ENDPOINT'LERİ ---

    // CREATE: Yeni departman ekle (Admin)
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @Valid @RequestBody DepartmentCreateRequest request) {
        
        DepartmentResponse response = departmentService.createDepartment(request);
        
        return ResponseEntity.ok(ApiResponse.<DepartmentResponse>builder()
                .success(true)
                .data(response)
                .message("Departman başarıyla oluşturuldu.")
                .build());
    }

    // READ: Tüm departmanları listele (Genel Liste - Sadece temel bilgiler)
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        
        List<DepartmentResponse> response = departmentService.getAllDepartments();
        
        return ResponseEntity.ok(ApiResponse.<List<DepartmentResponse>>builder()
                .success(true)
                .data(response)
                .build());
    }

    // READ: Tek bir departmanın detaylarını getir (Doktorlar dahil)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDetailResponse>> getDepartmentById(@PathVariable Long id) {
        
        DepartmentDetailResponse response = departmentService.getDepartmentById(id);
        
        return ResponseEntity.ok(ApiResponse.<DepartmentDetailResponse>builder()
                .success(true)
                .data(response)
                .build());
    }

    // UPDATE: Departman bilgilerini güncelle
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentUpdateRequest request) {
        
        DepartmentResponse response = departmentService.updateDepartment(id, request);
        
        return ResponseEntity.ok(ApiResponse.<DepartmentResponse>builder()
                .success(true)
                .data(response)
                .message("Departman bilgileri güncellendi.")
                .build());
    }

    // DELETE: Departmanı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
        
        departmentService.deleteDepartment(id);
        
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Departman başarıyla silindi.")
                .build());
    }

    // --- ÖZEL ENDPOINT'LER ---

    // CUSTOM: Departmana ait tüm doktorları getir
    @GetMapping("/{id}/doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDepartmentDoctors(@PathVariable Long id) {
        
        List<DoctorResponse> response = departmentService.getDepartmentDoctors(id);
        
        return ResponseEntity.ok(ApiResponse.<List<DoctorResponse>>builder()
                .success(true)
                .data(response)
                .build());
    }


    // CUSTOM: Departman resmini güncelle (Admin)
    @PutMapping("/{id}/image")
    public ResponseEntity<ApiResponse<String>> updateDepartmentImage(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) { // Frontend'in { "imageUrl": "..." } şeklinde gönderdiğini varsayıyoruz
        
        String imageUrl = body.get("imageUrl");
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new RuntimeException("Resim URL'i boş olamaz!");
        }

        String updatedImageUrl = departmentService.updateImage(id, imageUrl);
        
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .success(true)
                .data(updatedImageUrl)
                .message("Departman resmi güncellendi.")
                .build());
    }
}