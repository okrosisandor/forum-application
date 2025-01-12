package com.app.forum.mapper;

import org.springframework.stereotype.Service;

import com.app.forum.dto.RatingRequest;
import com.app.forum.dto.RatingResponse;
import com.app.forum.entity.Rating;

@Service
public class RatingMapper {

	public Rating toRating(RatingRequest ratingRequest) {
		return Rating.builder()
			.voteType(ratingRequest.getVoteType())
			.build();
	}
	
	public RatingResponse toRatingResponse(Rating rating) {
		return RatingResponse.builder()
			.userId(rating.getUser().getId())
			.answerId(rating.getAnswer().getId())
			.voteType(rating.getVoteType())
			.build();
	}
}
