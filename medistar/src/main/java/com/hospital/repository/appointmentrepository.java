package com.hospital.repository;

import com.hospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface appointmentrepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorNameAndAppointmentDateAndIsCancelledFalse(String doctorName, LocalDateTime date);
  
    List<Appointment> findByUserIdAndIsCancelledFalse(Long userId);
}