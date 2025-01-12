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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
	
	@Autowired
	private final AuthenticationService authenticationService;
	
	@PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ){
		authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(
		@RequestBody @Valid AuthenticationRequest request
	){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}


}
