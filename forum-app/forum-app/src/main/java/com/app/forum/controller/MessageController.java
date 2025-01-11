package com.app.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.MessageRequest;
import com.app.forum.dto.MessageResponse;
import com.app.forum.service.MessageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("messages")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<PageResponse<MessageResponse>> findAllMessagesByReceiver(
		@PathVariable int userId,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(messageService.findAllMessagesByReceiverId(userId, page, size));
	}
	
	@GetMapping("/sender/{userId}")
	public ResponseEntity<PageResponse<MessageResponse>> findAllMessagesBySender(
		@PathVariable int userId,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(messageService.findAllMessagesBySenderId(userId, page, size));
	}
	
	@GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadMessageCount(@RequestParam int userId) {
        long unreadCount = messageService.countUnreadMessages(userId);
        return ResponseEntity.ok(unreadCount);
    }

	@PostMapping("")
    public ResponseEntity<Integer> create(@Valid @RequestBody MessageRequest message) {
        return ResponseEntity.ok(messageService.createMessage(message));
    }
}
