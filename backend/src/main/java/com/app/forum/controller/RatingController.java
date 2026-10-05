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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("ratings")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Ratings", description = "Endpoints for upvoting, downvoting, or evaluating answer quality")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	@GetMapping("/{answerId}")
	@Operation(summary = "Get ratings for an answer", description = "Retrieves a complete list of all votes submitted for a specific answer.")
	public ResponseEntity<List<RatingResponse>> findAllRatingsByAnswerId(
			@PathVariable int answerId
	) {
		return ResponseEntity.ok(ratingService.findAllRatingsByAnswerId(answerId));
	}

	@PostMapping("")
    @Operation(summary = "Submit a rating", description = "Allows an authenticated user to rate or vote on a specific forum answer, updating its global rank.")
    public ResponseEntity<Long> createRating(@Valid @RequestBody RatingRequest rating) {
        return ResponseEntity.ok(ratingService.createRating(rating));
    }
}