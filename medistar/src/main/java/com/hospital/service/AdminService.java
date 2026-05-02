package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.Users;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.UserRepository;
import com.hospital.dto.AdminAppointmentResponseDTO;
import com.hospital.dto.DoctorInfo;
import com.hospital.dto.PatientInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
	  @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private AppointmentRepository appointmentRepository;

	    @Autowired
	    private DoctorRepository doctorRepository;

	    @Autowired
	    private DepartmentRepository departmentRepository;

	     
	    public List<Users> getAllUsers() {
	        return userRepository.findAll();
	    }

	     
	    @Transactional
	    public boolean deleteUser(Long userId) {
	        if (!userRepository.existsById(userId)) return false;
	        userRepository.deleteById(userId);
	        return true;
	    }

	     
	    public List<AdminAppointmentResponseDTO> getAllAppointments() {
	        
	        List<Appointment> appointments = appointmentRepository.findAll();
	        
	        return appointments.stream().map(appointment -> {
	            
	            // 1. Hasta bilgisini DTO'ya dönüştür
	            PatientInfo patientInfo = new PatientInfo(
	                appointment.getPatient().getId(),
	                appointment.getPatient().getFirstName(),
	                appointment.getPatient().getLastName(),
	                appointment.getPatient().getTcNo(),
	                appointment.getPatient().getPhone(),
	                appointment.getPatient().getEmail()
	            );

	            // 2. Doktor bilgisini DTO'ya dönüştür
	            DoctorInfo doctorInfo = new DoctorInfo(
	                appointment.getDoctor().getId(),
	                appointment.getDoctor().getAlias(),
	                appointment.getDoctor().getTitle()
	            );

	            // 3. Ana DTO'yu birleştirip döndür
	            return new AdminAppointmentResponseDTO(
	                appointment.getId(),
	                appointment.getAppointmentDate(),
	                appointment.getAppointmentTime(),
	                appointment.getStatus(),
	                appointment.getPriority(),
	                appointment.getNotes(),
	                patientInfo,
	                doctorInfo
	            );
	            
	        }).collect(Collectors.toList());
	    }

	     
	    @Transactional
	    public boolean deleteAppointment(Long appointmentId) {
	        if (!appointmentRepository.existsById(appointmentId)) return false;
	        appointmentRepository.deleteById(appointmentId);
	        return true;
	    }

	     
	    public Map<String, Object> getStats() {
	        Map<String, Object> stats = new HashMap<>();

	        stats.put("totalUsers", userRepository.count());
	        stats.put("totalAppointments", appointmentRepository.count());

	      

	        return stats;
	    }

	     
	    @Transactional
	    public Doctor addDoctor(Doctor doctor) {
	        return doctorRepository.save(doctor);
	    }

	     
	    @Transactional
	    public boolean deleteDoctor(Long doctorId) {
	        if (!doctorRepository.existsById(doctorId)) return false;
	        doctorRepository.deleteById(doctorId);
	        return true;
	    }

	     
	    @Transactional
	    public Department addDepartment(Department department) {
	        return departmentRepository.save(department);
	    }

	     
	    @Transactional
	    public boolean deleteDepartment(Long deptId) {
	        if (!departmentRepository.existsById(deptId)) return false;
	        departmentRepository.deleteById(deptId);
	        return true;
	    }
}
