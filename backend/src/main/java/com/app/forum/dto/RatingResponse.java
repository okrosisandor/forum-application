package com.app.forum.dto;

import com.app.forum.enums.VoteType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RatingResponse {
	
	private int userId;
	
	private int answerId;
	
	private VoteType voteType;

}
