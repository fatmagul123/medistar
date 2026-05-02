package com.hospital.dto;

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


public class PatientUpdateRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private PriorityStatus priorityStatus;
}