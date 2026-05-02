package com.hospital.mapper;

import com.hospital.dto.AppointmentCreateRequest;
import com.hospital.dto.AppointmentResponse;
import com.hospital.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {

    // Entity'den Response DTO'ya dönüşüm
    @Mapping(source = "patient.id", target = "patientId")
    // Ad ve soyadı birleştirmek için özel java expression yazıyoruz
    @Mapping(target = "patientFullName", expression = "java(appointment.getPatient().getFirstName() + \" \" + appointment.getPatient().getLastName())")
    @Mapping(source = "patient.email", target = "userEmail")
    
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.alias", target = "doctorName")
    
    @Mapping(source = "doctor.department.id", target = "deptId")
    @Mapping(source = "doctor.department.name", target = "deptName")
    AppointmentResponse toResponse(Appointment appointment);

    // CreateRequest'ten Entity'e dönüşüm (ID'ler Service katmanında set edileceği için burada ignore ediyoruz)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "status", constant = "PENDING") // Yeni randevu her zaman bekliyor statüsündedir
    Appointment toEntity(AppointmentCreateRequest request);
}