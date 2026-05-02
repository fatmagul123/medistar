package com.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.hospital.dto.PatientCreateRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.dto.PatientUpdateRequest;
import com.hospital.entity.Patient;

//nullValuePropertyMappingStrategy'i buraya taşıdık
@Mapper(
 componentModel = "spring", 
 unmappedTargetPolicy = ReportingPolicy.IGNORE,
 nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientMapper {

 // 1. Entity -> DTO (Read)
 @Mapping(source = "userAccount.id", target = "userId")
 PatientResponse toResponse(Patient patient);

 // 2. DTO -> Entity (Create)
 @Mapping(target = "id", ignore = true)
 @Mapping(target = "userAccount", ignore = true) 
 Patient toEntity(PatientCreateRequest request);

 // 3. DTO -> (Update)

 @Mapping(target = "id", ignore = true)
 @Mapping(target = "tcNo", ignore = true) 
 @Mapping(target = "email", ignore = true) 
 @Mapping(target = "userAccount", ignore = true) 
 void updateEntity(@MappingTarget Patient patient, PatientUpdateRequest request);
}