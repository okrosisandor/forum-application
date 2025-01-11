package com.app.forum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.forum.dto.UserResponse;
import com.app.forum.entity.User;

@Service
public class UserMapper {
	
	@Autowired
	private RoleMapper roleMapper;

	public UserResponse toUserResponse(User user) {
		return UserResponse.builder()
			.userId(user.getId())
			.displayName(user.getDisplayName())
			.email(user.getEmail())
			.dateOfBirth(user.getDateOfBirth())
			.roles(roleMapper.toRoleResponseList(user.getRoles()))
			.accountLocked(user.isAccountLocked())
			.enabled(user.isEnabled())
			.build();
	}
}
