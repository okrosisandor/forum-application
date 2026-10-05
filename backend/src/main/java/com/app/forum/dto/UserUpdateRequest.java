package com.app.forum.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserUpdateRequest {

	private String displayName;
	
	private String email;
	
	private LocalDate dateOfBirth;
	
	private String password;
	
}
