package com.app.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.QuestionRequest;
import com.app.forum.dto.QuestionResponse;
import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;
import com.app.forum.service.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestionById(
    		@PathVariable int questionId){
        QuestionResponse question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }
	
	@GetMapping
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestions(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(questionService.findAllQuestions(page, size));
	}
	
	@GetMapping("/owner")
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestionsByOwner(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size,
		Authentication user
	) {
		return ResponseEntity.ok(questionService.findAllQuestionsByOwner(page, size, user));
	}
	
	@GetMapping("/{mainCategory}/{subCategory}")
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestionsForSubcategory(
		@PathVariable MainCategoryType mainCategory,
		@PathVariable SubCategoryType subCategory,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(questionService.findAllQuestionsForSubcategory(mainCategory, subCategory, page, size));
	}
	
	@GetMapping("/search/{searchText}")
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestionsBySearchText(
		@PathVariable String searchText,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(questionService.findAllQuestionsBySearchText(searchText, page, size));
	}


	@PostMapping("")
    public ResponseEntity<Integer> create(@Valid @RequestBody QuestionRequest question) {
        return ResponseEntity.ok(questionService.createQuestion(question));
    }
}
