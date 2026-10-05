package com.app.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.dto.AuthenticationRequest;
import com.app.forum.dto.AuthenticationResponse;
import com.app.forum.dto.RegistrationRequest;
import com.app.forum.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Authentication", description = "User management endpoints for registration, login, and token generation")
public class AuthenticationController {
	
	@Autowired
	private final AuthenticationService authenticationService;
	
	@PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided registration details.")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ){
		authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }
	
	@PostMapping("/login")
	@Operation(summary = "Authenticate user", description = "Validates user credentials and returns a secure JWT access token.")
	public ResponseEntity<AuthenticationResponse> authenticate(
		@RequestBody @Valid AuthenticationRequest request
	){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}