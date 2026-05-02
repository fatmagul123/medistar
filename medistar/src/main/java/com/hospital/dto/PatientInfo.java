package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientInfo {
    private Long id;
    private String firstName;
    private String lastName;
    private String tcNo;
    private String phone;
    private String email;
}