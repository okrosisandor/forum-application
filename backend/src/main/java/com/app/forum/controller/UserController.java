package com.app.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.dto.UserUpdateRequest;
import com.app.forum.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "User Profile", description = "Endpoints for managing user account data and profiles")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update user profile information", description = "Partially updates an existing user's account and profile data with the provided parameters.")
    public ResponseEntity<?> register(
    		@PathVariable int userId,
            @RequestBody @Valid UserUpdateRequest user
    ){
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }
}