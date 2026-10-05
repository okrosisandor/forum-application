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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("questions")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Questions", description = "Core endpoints for creating, retrieving, and searching forum questions")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/{questionId}")
    @Operation(summary = "Get a question by ID", description = "Retrieves the detailed information and content of a specific question based on its unique identifier.")
    public ResponseEntity<QuestionResponse> getQuestionById(
    		@PathVariable int questionId){
        QuestionResponse question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }
	
	@GetMapping
	@Operation(summary = "Get all questions", description = "Retrieves a paginated list of all questions available across the entire forum platform.")
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestions(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(questionService.findAllQuestions(page, size));
	}
	
	@GetMapping("/owner")
	@Operation(summary = "Get questions by owner", description = "Retrieves a paginated list of all forum questions created by the currently authenticated user.")
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestionsByOwner(
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size,
		Authentication user
	) {
		return ResponseEntity.ok(questionService.findAllQuestionsByOwner(page, size, user));
	}
	
	@GetMapping("/{mainCategory}/{subCategory}")
	@Operation(summary = "Get questions by subcategory", description = "Retrieves a paginated list of questions filtered by a specific combination of main category and subcategory.")
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestionsForSubcategory(
		@PathVariable MainCategoryType mainCategory,
		@PathVariable SubCategoryType subCategory,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(questionService.findAllQuestionsForSubcategory(mainCategory, subCategory, page, size));
	}
	
	@GetMapping("/search/{searchText}")
	@Operation(summary = "Search questions", description = "Performs a full-text search on titles and content, returning a paginated list of matching questions.")
	public ResponseEntity<PageResponse<QuestionResponse>> findAllQuestionsBySearchText(
		@PathVariable String searchText,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {
		return ResponseEntity.ok(questionService.findAllQuestionsBySearchText(searchText, page, size));
	}

	@PostMapping("")
    @Operation(summary = "Create a new question", description = "Allows an authenticated user to submit and publish a new discussion question on the forum.")
    public ResponseEntity<Integer> create(@Valid @RequestBody QuestionRequest question) {
        return ResponseEntity.ok(questionService.createQuestion(question));
    }
}