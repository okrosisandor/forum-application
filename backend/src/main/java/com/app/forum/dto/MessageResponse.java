package com.app.forum.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageResponse {
	
	private Integer messageId;
	
	private Integer receiverId;
	
	private String from;
	
	private String to;
	
	private String message;
	
	private LocalDateTime createdDate;
	
	private boolean globalMessage;
	
	private boolean createdByAdmin;
}
