package com.app.forum.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnswerResponse {
	
	private Integer answerId;
	
	private Integer userId;
	
	private String createdUser;
	
	private Integer questionId;
	
	private String content;
	
	private LocalDateTime createdDate;
	
	private int upvoteNumber;
	
	private int downvoteNumber;
	
	private int userRating;
	
	private boolean userHasVotes;
	
	private List<RatingResponse> ratings;
	
	private boolean reported;
	
	private LocalDateTime firstReported;
	
	private LocalDateTime lastReported;
	
	private int reportedNumber;

}
