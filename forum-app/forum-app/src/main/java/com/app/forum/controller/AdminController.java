package com.app.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.AnswerResponse;
import com.app.forum.dto.GlobalMessageRequest;
import com.app.forum.service.AnswerService;
import com.app.forum.service.GlobalMessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private GlobalMessageService globalMessageService;
	

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Welcome to the admin dashboard!";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage-users")
    public String manageUsers() {
        return "Here you can manage users.";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reported")
	public ResponseEntity<PageResponse<AnswerResponse>> findAllReportedAnswers(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(answerService.findAllReportedAnswers(page, size));
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/cancel-reported/{answerId}")
    public ResponseEntity<Integer> cancelReportedStatus(
		@PathVariable int answerId
	) {
		return ResponseEntity.ok(answerService.cancelReportedStatus(answerId));
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Integer> deleteAnswer(
		@PathVariable int answerId
	) {
		return ResponseEntity.ok(answerService.deleteAnswer(answerId));
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/global-message")
    public ResponseEntity<Integer> createGloabalMessage(@Valid @RequestBody GlobalMessageRequest message) {
        return ResponseEntity.ok(globalMessageService.createGlobalMessage(message));
    }
}
