package com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.entity.Users;
import com.hospital.service.HospitalService;

@RestController @RequestMapping("/api/admin")
public class AdminController {
    @Autowired private HospitalService service;

    @PostMapping("/add-doctor")
    public ResponseEntity<?> addDoctor(@RequestBody Users doctor) {
        return ResponseEntity.ok(service.addDoctor(doctor));
    }
}