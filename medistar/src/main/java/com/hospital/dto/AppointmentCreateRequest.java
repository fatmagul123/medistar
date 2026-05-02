package com.hospital.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital.enums.PriorityStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateRequest {
    private Long patientId; 
    private Long doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String notes;
    private PriorityStatus priority;
    

}