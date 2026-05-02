package com.hospital.dto;
import com.hospital.enums.Gender;
import com.hospital.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserResponse {
	private Long id;
    private String email; 
    private String firstname;
    private String lastname;
    private String phone;
    private LocalDate dayOfBirth;
    private Gender gender;
   
    private Role role;
    private boolean isActive;

    
    private Long patientProfileId;
    private Long doctorProfileId;
}
