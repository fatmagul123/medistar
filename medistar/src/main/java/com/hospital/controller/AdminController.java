package com.hospital.controller;

import com.hospital.dto.AdminAppointmentResponseDTO;
import com.hospital.dto.ApiResponse;
import com.hospital.dto.DoctorCreateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.entity.Appointment;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.Users;
import com.hospital.service.AdminService;
import com.hospital.service.DoctorService;

import jakarta.validation.Valid;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private DoctorService doctorService;

    // --- Kullanıcı ---
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId) {
        return createDeleteResponse(adminService.deleteUser(userId), "Kullanıcı");
    }

    // --- Randevu ---
    @GetMapping("/appointments")
    public ResponseEntity<List<AdminAppointmentResponseDTO>> getAllAppointments() {
        return ResponseEntity.ok(adminService.getAllAppointments());
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<Map<String, Object>> deleteAppointment(@PathVariable Long appointmentId) {
        return createDeleteResponse(adminService.deleteAppointment(appointmentId), "Randevu");
    }


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



    // --- Bölüm ---
    @PostMapping("/departments")
    public ResponseEntity<Map<String, Object>> addDepartment(@RequestBody Department department) {
        Map<String, Object> response = new HashMap<>();
        if (department.getName() == null) {
            response.put("success", false);
            response.put("message", "Eksik bilgi");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("success", true);
        response.put("department", adminService.addDepartment(department));
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/departments/{deptId}")
    public ResponseEntity<Map<String, Object>> deleteDepartment(@PathVariable Long deptId) {
        return createDeleteResponse(adminService.deleteDepartment(deptId), "Bölüm");
    }

    // --- Yardımcı Metot (Tekrarlayan kodları önlemek için) ---
    private ResponseEntity<Map<String, Object>> createDeleteResponse(boolean isDeleted, String entityName) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", isDeleted);
        if (!isDeleted) {
            response.put("message", entityName + " bulunamadı.");
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }
}