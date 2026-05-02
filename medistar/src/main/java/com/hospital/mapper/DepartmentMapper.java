package com.hospital.mapper;


import com.hospital.dto.DepartmentCreateRequest;
import com.hospital.dto.DepartmentDetailResponse;
import com.hospital.dto.DepartmentResponse;
import com.hospital.dto.DepartmentUpdateRequest;
import com.hospital.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DepartmentMapper {

    DepartmentResponse toResponse(Department department);
    
    DepartmentDetailResponse toDetailResponse(Department department);

    Department toEntity(DepartmentCreateRequest request);

    void updateEntity(@MappingTarget Department department, DepartmentUpdateRequest request);
}