package com.hospital.controller;

import com.hospital.dto.AppointmentCreateRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.dto.AppointmentUpdateRequest;
import com.hospital.dto.ApiResponse;
import com.hospital.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // --- 1. RANDEVU OLUŞTURMA ---
    // Güvenlik olmadığı için email adresini RequestParam (URL'den) olarak alıyoruz.
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody AppointmentCreateRequest request,
            @RequestParam String email) {
        
        AppointmentResponse response = appointmentService.createAppointment(request, email);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .data(response)
                        .message("Randevu başarıyla oluşturuldu.")
                        .build()
        );
    }

    // --- 2. HASTAYA GÖRE RANDEVU LİSTELEME ---
    // Email bilgisini yine URL parametresi olarak alıyoruz
    @GetMapping("/my-appointments")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getUserAppointments(
            @RequestParam String email) {
        
        List<AppointmentResponse> response = appointmentService.getUserAppointments(email);
        
        return ResponseEntity.ok(
                ApiResponse.<List<AppointmentResponse>>builder()
                        .success(true)
                        .data(response)
                        .message("Randevular getirildi.")
                        .build()
        );
    }

    // --- 3. RANDEVU DURUMU GÜNCELLEME ---
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointmentStatus(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateRequest request) {
        
        AppointmentResponse response = appointmentService.updateAppointmentStatus(id, request);
        
        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .data(response)
                        .message("Randevu durumu güncellendi.")
                        .build()
        );
    }

    // --- 4. RANDEVU SİLME ---
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable Long id) {
        
        appointmentService.deleteAppointment(id);
        
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Randevu silindi.")
                        .build()
        );
    }

    // --- 5. DOKTORUN DOLU SAATLERİNİ GETİRME ---
    @GetMapping("/booked-slots")
    public ResponseEntity<ApiResponse<List<String>>> getBookedSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        List<String> bookedSlots = appointmentService.getBookedSlots(doctorId, date);
        
        return ResponseEntity.ok(
                ApiResponse.<List<String>>builder()
                        .success(true)
                        .data(bookedSlots)
                        .build()
        );
    }
}