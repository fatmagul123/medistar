package com.hospital.dto;


import com.hospital.enums.PriorityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PatientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String tcNo;
    private String email;
    private String phone;
    private PriorityStatus priorityStatus;
    private Long userId;
   
    

}
