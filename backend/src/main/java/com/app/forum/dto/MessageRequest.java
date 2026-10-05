package com.app.forum.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageRequest {
	
	private String from;
	
	private String receiverUserName;
	
	@Size(min = 10, max = 5000, message = "Message should be between 10 and 5000 characters.")
	private String message;

}
