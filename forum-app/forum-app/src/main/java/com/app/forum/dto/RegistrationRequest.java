package com.app.forum.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

	@NotBlank(message = "Username is mandatory")
	private String displayName;
	
	@Email(message = "Invalid email")
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	private LocalDate dateOfBirth;
	
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, message = "Password should be minimum 8 characters")
	private String password;

}
