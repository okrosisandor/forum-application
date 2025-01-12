package com.app.forum.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.forum.dto.AuthenticationRequest;
import com.app.forum.dto.AuthenticationResponse;
import com.app.forum.dto.RegistrationRequest;
import com.app.forum.entity.User;
import com.app.forum.exception.RoleNotInitializedException;
import com.app.forum.mapper.UserMapper;
import com.app.forum.repository.RoleRepository;
import com.app.forum.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private final RoleRepository roleRepository;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final JwtService jwtService;
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private UserMapper userMapper;
	
	public void register(@Valid RegistrationRequest request) {
		var userRole = roleRepository.findByName("USER")
				.orElseThrow(() -> new RoleNotInitializedException("ROLE USER was not initialized"));
		
		var user = User.builder()
				.username(request.getDisplayName())
				.email(request.getEmail())
				.displayName(request.getDisplayName())
				.dateOfBirth(request.getDateOfBirth())
				.password(passwordEncoder.encode(request.getPassword()))
				.accountLocked(false)
				.enabled(true)
				.roles(List.of(userRole))
				.build();
		
		userRepository.save(user);
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		var auth = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
			)
		);
		var claims = new HashMap<String, Object>();
		var user = ((User) auth.getPrincipal());
//		claims.put("username", user.getUsername());
		var jwtToken = jwtService.generateToken(claims, user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.user(userMapper.toUserResponse(user))
			.build();
	}

}
