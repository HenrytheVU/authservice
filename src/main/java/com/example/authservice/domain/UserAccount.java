package com.example.authservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAccount {

	public UserAccount(String email, String password, String name, String salt, LocalDateTime createdTime) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.salt = salt;
		this.createdTime = createdTime;
	}

	@Id
	@GeneratedValue
	private UUID id;
	private String email;
	@With
	private String password;
	private String name;

	@With
	private String salt;

	private LocalDateTime createdTime;

	@With
	private LocalDateTime lastLogin;

}
