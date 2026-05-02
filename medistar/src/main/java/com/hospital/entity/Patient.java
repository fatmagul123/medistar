package com.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.hospital.enums.PriorityStatus;


@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
 
    @Column(name = "tc_no", unique = true)
    private String tcNo;
    
    @Column(unique = true)
    private String email;

    private String phone;


    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "priority_status")
    private PriorityStatus priorityStatus;
    
 // Patient.java'da ilişki şöyle olmalı
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users userAccount;
}
