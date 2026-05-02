package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import com.hospital.enums.Gender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserCreateRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private LocalDate dayOfBirth;
    private Gender gender;
    private String tcNo;
}
