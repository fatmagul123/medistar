package com.hospital.service;

import com.hospital.entity.*;
import com.hospital.repository.*;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Appointment appRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Appointment takeAppointment(Appointment app) {
        return ((Appointment) appRepo).save(app);
    }
    public void cancelAppointment(Long id) {
      
        Appointment app = ((Object) appRepo.findById(id))
                .elsethrow(() -> new RuntimeException("Randevu bulunamadı"));
        
        app.setCancelled(true);
        appRepo.save(app);
    }
    
    public Appointment bookAppointment(Appointment app) {
        if (app.getPriorityType() != null) {
            switch(app.getPriorityType()) {
                case "ACIL": app.setPriorityScore(1); break;
                case "HAMILE": app.setPriorityScore(2); break;
                default: app.setPriorityScore(3);
            }
        }
        return appRepo.save(app);
    }

	public @Nullable Object addDoctor(Users doctor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}