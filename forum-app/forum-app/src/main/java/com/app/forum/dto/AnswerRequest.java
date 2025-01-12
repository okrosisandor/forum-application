package com.app.forum.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnswerRequest {

    private int userId;
	
    private int questionId;
	
    @Size(min = 10, max = 5000, message = "Answer should be between 10 and 10000 characters.")
	private String content;
	
}
