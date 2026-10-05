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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Management", description = "Administrative operations for content moderation and system announcements")
public class AdminController {
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private GlobalMessageService globalMessageService;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reported")
    @Operation(summary = "Get all reported answers", description = "Retrieves a paginated list of answers that have been flagged or reported by users.")
	public ResponseEntity<PageResponse<AnswerResponse>> findAllReportedAnswers(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(answerService.findAllReportedAnswers(page, size));
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/cancel-reported/{answerId}")
    @Operation(summary = "Dismiss report status", description = "Cancels the reported status of a specific answer, marking it as safe.")
    public ResponseEntity<Integer> cancelReportedStatus(
		@PathVariable int answerId
	) {
		return ResponseEntity.ok(answerService.cancelReportedStatus(answerId));
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{answerId}")
    @Operation(summary = "Delete an answer", description = "Permanently deletes a reported or inappropriate answer from the forum.")
    public ResponseEntity<Integer> deleteAnswer(
		@PathVariable int answerId
	) {
		return ResponseEntity.ok(answerService.deleteAnswer(answerId));
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/global-message")
    @Operation(summary = "Create system-wide message", description = "Publishes a global announcement or message visible to all application users.")
    public ResponseEntity<Integer> createGloabalMessage(@Valid @RequestBody GlobalMessageRequest message) {
        return ResponseEntity.ok(globalMessageService.createGlobalMessage(message));
    }
}