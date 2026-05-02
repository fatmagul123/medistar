package com.hospital.dto;



import com.hospital.enums.AppointmentStatus;
import com.hospital.enums.PriorityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAppointmentResponseDTO {

    // Randevu Bilgileri
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private PriorityStatus priority;
    private String notes;

    // Hasta ve Doktor objeleri
    private PatientInfo patient;
    private DoctorInfo doctor;

    // --- İÇ İÇE (NESTED) SINIflAR ---

    

    
}