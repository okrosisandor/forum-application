package com.app.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.dto.RatingRequest;
import com.app.forum.dto.RatingResponse;
import com.app.forum.service.RatingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("ratings")
@CrossOrigin(origins = "http://localhost:4200")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@GetMapping("/{answerId}")
	public ResponseEntity<List<RatingResponse>> findAllRatingsByAnswerId(
			@PathVariable int answerId
	) {
		return ResponseEntity.ok(ratingService.findAllRatingsByAnswerId(answerId));
	}

	@PostMapping("")
    public ResponseEntity<Long> createRating(@Valid @RequestBody RatingRequest rating) {
        return ResponseEntity.ok(ratingService.createRating(rating));
    }
}
