package com.app.forum.controller;

import java.security.Principal;

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
import com.app.forum.dto.AnswerRequest;
import com.app.forum.dto.AnswerResponse;
import com.app.forum.service.AnswerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("answers")
@CrossOrigin(origins = "http://localhost:4200")
public class AnswerController {
	
	@Autowired
	AnswerService answerService;
	
	@GetMapping("/{questionId}")
	public ResponseEntity<PageResponse<AnswerResponse>> findAllAnswersByQuestionId(
		@PathVariable int questionId,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(answerService.findAllAnswersByQuestionId(questionId, page, size));
	}

	@PostMapping("")
    public ResponseEntity<Integer> create(@Valid @RequestBody AnswerRequest answer) {
        return ResponseEntity.ok(answerService.createAnswer(answer));
    }
	
	@GetMapping("/report/{answerId}")
    public ResponseEntity<Integer> reportAnswer(@PathVariable int answerId, Principal principal) {
        return ResponseEntity.ok(answerService.reportAnswer(answerId, principal));
    }
}
