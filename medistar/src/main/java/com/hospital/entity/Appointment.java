package com.hospital.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private String priorityType; 
    public String getPriorityType() { return priorityType; }
    public void setPriorityScore(int score) { }
    public String getDoctorName() { return doctorName; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setCancelled(boolean cancelled) { }
	
    public Appointment save(Appointment app) {
		// TODO Auto-generated method stub
		return null;
	}
	public Object findById(Long id2) {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getPatient() {
		// TODO Auto-generated method stub
		return null;
	}
	
} 
