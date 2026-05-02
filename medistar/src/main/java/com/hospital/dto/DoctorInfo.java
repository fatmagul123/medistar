package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfo {
    private Long id;
    private String alias;
    private String title;
    // İsterseniz department name'i de buraya ekleyebilirsiniz.
}