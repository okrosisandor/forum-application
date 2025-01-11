package com.app.forum.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
	
	private int userId;
	
	private String displayName;
	
	private String email;
	
	private LocalDate dateOfBirth;
	
	private List<RoleResponse> roles;
	
	private boolean accountLocked;
	
	private boolean enabled;

}
