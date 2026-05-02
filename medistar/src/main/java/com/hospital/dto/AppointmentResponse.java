package com.hospital.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital.enums.AppointmentStatus;
import com.hospital.enums.PriorityStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private String patientFullName;
    private String userEmail;
    private Long doctorId;
    private String doctorName;
    private Long deptId;
    private String deptName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private String notes;  
    private PriorityStatus priority;
}