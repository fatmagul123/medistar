package com.hospital.entity;

import com.hospital.enums.Gender;
import com.hospital.enums.Role;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;  
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="e_mail",nullable = false, unique = true)
    private String email;
    
    @Column(name="firstname")
    private String firstname;
    
    @Column(name="lastname")
    private String lastname;

    @Column(nullable = false)
    private String password;
    
    @Column(name="phone",length=10)
    private String phone;
    
    @Column(name="day_of_birth")
    private LocalDate dayOfBirth;
    
    @Column(name = "tc_no")  
    private String tcNo;
    
    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean isActive = true;

}