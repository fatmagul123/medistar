package com.hospital.mapper;

import com.hospital.dto.DoctorCreateRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.dto.DoctorUpdateRequest;
import com.hospital.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "userAccount.id", target = "userId")
    DoctorResponse toResponse(Doctor doctor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true) // Service'de veritabanından bulunup set edilecek
    @Mapping(target = "userAccount", ignore = true)
    Doctor toEntity(DoctorCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    void updateEntity(@MappingTarget Doctor doctor, DoctorUpdateRequest request);
}