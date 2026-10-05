package com.app.forum.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.forum.dto.RoleResponse;
import com.app.forum.entity.Role;

@Service
public class RoleMapper {

	public RoleResponse toRoleResponse(Role role) {
		return RoleResponse.builder()
				.name(role.getName())
				.build();
	}
	
	public List<RoleResponse> toRoleResponseList(List<Role> roles) {
        return roles.stream()
                    .map(this::toRoleResponse)
                    .collect(Collectors.toList());
    }
}
