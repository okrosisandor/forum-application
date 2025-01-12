package com.app.forum.mapper;

import org.springframework.stereotype.Service;

import com.app.forum.dto.MessageRequest;
import com.app.forum.dto.MessageResponse;
import com.app.forum.entity.Message;

@Service
public class MessageMapper {

	public Message toMessage(MessageRequest messageRequest) {
		return Message.builder()
			.receiverUserName(messageRequest.getReceiverUserName() != null? messageRequest.getReceiverUserName() : "")
			.message(messageRequest.getMessage())
			.build();
	}
	
	public MessageResponse toMessageResponse(Message message) {
		return MessageResponse.builder()
			.messageId(message.getId())
			.receiverId(message.getUser() != null ? message.getUser().getId() : null)
			.from(message.getFromUsername())
			.to(message.getReceiverUserName() != null ? message.getReceiverUserName() : "")
			.message(message.getMessage())
			.createdDate(message.getCreatedDate())
			.globalMessage(message.isGlobalMessage())
			.createdByAdmin(message.isCreatedByAdmin())
			.build();	
	}
}
