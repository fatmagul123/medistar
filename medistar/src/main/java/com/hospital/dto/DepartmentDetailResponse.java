package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String img;
    private List<DoctorResponse> doctors;
    private List<DoctorResponse> priorityDoctors;
}