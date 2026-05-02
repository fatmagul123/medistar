package com.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.hospital.dto.UserCreateRequest;
import com.hospital.dto.UserResponse;
import com.hospital.dto.UserUpdateRequest;
import com.hospital.entity.Users;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    // Patient ve Doctor profile ID'leri genelde User entity'sinde direkt bulunmadığı için 
    // bunları Service katmanında manuel set etmek daha sağlıklıdır. MapStruct uyarı vermesin diye ignore ediyoruz.
    @Mapping(target = "patientProfileId", ignore = true)
    @Mapping(target = "doctorProfileId", ignore = true)
    UserResponse toResponse(Users user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "ROLE_PATIENT") // Varsayılan kayıt rolü
    @Mapping(target = "active", constant = "true")
    Users toEntity(UserCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "tcNo", ignore = true)
    void updateEntity(@MappingTarget Users user, UserUpdateRequest request);
}