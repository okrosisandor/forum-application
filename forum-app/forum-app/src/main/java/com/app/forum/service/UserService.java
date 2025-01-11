package com.app.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.forum.dto.UserResponse;
import com.app.forum.dto.UserUpdateRequest;
import com.app.forum.entity.User;
import com.app.forum.mapper.UserMapper;
import com.app.forum.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserMapper userMapper;

	public UserResponse updateUser(int userId, @Valid UserUpdateRequest updatedUser) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

	    if (updatedUser.getDisplayName() != null && !updatedUser.getDisplayName().equals(user.getUsername())) {
	        user.setUsername(updatedUser.getDisplayName());
	    }
	    
	    if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(user.getEmail())) {
	        user.setEmail(updatedUser.getEmail());
	    }

	    if (updatedUser.getDateOfBirth() != null && !updatedUser.getDateOfBirth().equals(user.getDateOfBirth())) {
	        user.setDateOfBirth(updatedUser.getDateOfBirth());
	    }

	    if (updatedUser.getPassword() != null && !updatedUser.getPassword().trim().isEmpty()) {
	        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
	    }

	    userRepository.save(user);
	    UserResponse response = userMapper.toUserResponse(user);
	    
	    return response;
	}

}
