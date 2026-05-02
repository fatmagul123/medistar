package com.hospital.service;


import com.hospital.dto.UserCreateRequest;
import com.hospital.dto.UserCreateRequest;
import com.hospital.entity.Patient;
import com.hospital.enums.Role;
import com.hospital.entity.Users;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class AuthService {

	@Autowired
    private UserRepository userRepository;


    public String login(UserCreateRequest dto) {
        // 1. Kullanıcıyı email'e göre bul
        /*Users user = userRepository.findByUsername(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        // 2. Şifre kontrol 
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Hatalı şifre!");
        }

        // 3. Başarılıysa mesaj dön 
        return "Giriş başarılı! Hoş geldin, rolünüz: " + user.getRole().name();
    }
*/return null;
  }	}