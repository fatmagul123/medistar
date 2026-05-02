package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import com.hospital.enums.Gender;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
	private String firstname;
    private String lastname;
    private String phone;
    private LocalDate dayOfBirth;
    private Gender gender;
}
