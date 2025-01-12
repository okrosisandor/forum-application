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

@RestController
@RequestMapping("notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class GlobalMessageController {
	
	@Autowired
	private GlobalMessageService globalMessageService;

	@GetMapping("/sender")
	public ResponseEntity<PageResponse<GlobalMessageResponse>> findAllGlobalMessages(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(globalMessageService.findAllMessages(page, size));
	}
}
