package com.app.forum.mapper;

import org.springframework.stereotype.Service;

import com.app.forum.dto.GlobalMessageRequest;
import com.app.forum.dto.GlobalMessageResponse;
import com.app.forum.entity.GlobalMessage;

@Service
public class GlobalMessageMapper {

	public GlobalMessage toGlobalMessage(GlobalMessageRequest messageRequest) {
		return GlobalMessage.builder()
			.message(messageRequest.getMessage())
			.build();
	}
	
	public GlobalMessageResponse toGlobalMessageResponse(GlobalMessage message) {
		return GlobalMessageResponse.builder()
			.messageId(message.getId())
			.from(message.getUser().getDisplayName())
			.message(message.getMessage())
			.createdDate(message.getCreatedDate())
			.globalMessage(message.isGlobalMessage())
			.createdByAdmin(message.isCreatedByAdmin())
			.build();
				
	}
}
