package com.app.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.GlobalMessageResponse;
import com.app.forum.service.GlobalMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("notifications")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Notifications", description = "Endpoints for fetching global system announcements and broadcasted notifications")
public class GlobalMessageController {
	
	@Autowired
	private GlobalMessageService globalMessageService;

	@GetMapping("/sender")
	@Operation(summary = "Get all global messages", description = "Retrieves a paginated list of all system-wide broadcasted messages and announcements.")
	public ResponseEntity<PageResponse<GlobalMessageResponse>> findAllGlobalMessages(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(globalMessageService.findAllMessages(page, size));
	}
}