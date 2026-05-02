package com.hospital.repository;

import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // Hastaları öncelik seviyesine (1'den 5'e doğru) sıralayarak getirir
    @Query("SELECT p FROM Patient p ORDER BY p.priorityStatus ASC")
    List<Patient> findAllByOrderByPriorityStatusAsc();

	boolean existsByTcNo(String tcNo);
	Optional<Patient> findByEmail(String email);
}