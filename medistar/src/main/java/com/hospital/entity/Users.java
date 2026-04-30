package com.hospital.entity;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;  
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users{

	@Id
	private Long id;
	
	@Column(name = "username" , length = 30)
	private String username;
	
	@Column(name = "password" , length = 150)
	private String password;

	public @Nullable CharSequence getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPassword(@Nullable String encode) {
		// TODO Auto-generated method stub
		
	}

	public void setRole(String string) {
		// TODO Auto-generated method stub
		
	}


}