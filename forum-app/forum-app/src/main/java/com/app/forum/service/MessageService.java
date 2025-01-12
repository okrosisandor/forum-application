package com.app.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.GlobalMessageRequest;
import com.app.forum.dto.MessageRequest;
import com.app.forum.dto.MessageResponse;
import com.app.forum.entity.GlobalMessage;
import com.app.forum.entity.Message;
import com.app.forum.entity.User;
import com.app.forum.exception.InvalidRecipientException;
import com.app.forum.mapper.GlobalMessageMapper;
import com.app.forum.mapper.MessageMapper;
import com.app.forum.repository.MessageRepository;
import com.app.forum.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	public PageResponse<MessageResponse> findAllMessagesByReceiverId(int receiverId, int page, int size) {
		messageRepository.markAllAsReadByReceiverId(receiverId);
		
		Pageable pageable = createPageable(page, size);
		Page<Message> messages = messageRepository.findAllByReceiverId(receiverId, pageable);
		
		return mapToPageResponse(messages, page, size);
	}
	
	public PageResponse<MessageResponse> findAllMessagesBySenderId(int senderId, int page, int size) {
		Pageable pageable = createPageable(page, size);
		
		User senderUser = userRepository.findById(senderId)
        		.orElseThrow(() -> new EntityNotFoundException("User with id " + senderId + " not found"));
		
		Page<Message> messages = messageRepository.findAllBySender(senderUser.getDisplayName(), pageable);
		return mapToPageResponse(messages, page, size);
	}
	
	public Integer createMessage(MessageRequest messageRequest) {
		
		if(messageRequest.getFrom().equalsIgnoreCase(messageRequest.getReceiverUserName())) {
			throw new InvalidRecipientException("You can not send a message to yourself.");
		}
		
	    User senderUser = userRepository.findByDisplayName(messageRequest.getFrom())
	            .orElseThrow(() -> new EntityNotFoundException("User with username " + messageRequest.getReceiverUserName() + " not found."));

	    User receiverUser = userRepository.findByDisplayName(messageRequest.getReceiverUserName())
	            .orElseThrow(() -> new EntityNotFoundException("User with username " + messageRequest.getReceiverUserName() + " not found."));
	    
	    Message message = messageMapper.toMessage(messageRequest);
	    message.setUser(receiverUser);
	    message.setFromUsername(senderUser.getDisplayName());
	    message.setReceiverUserName(receiverUser.getDisplayName());
	    message.setRead(false);
	    message.setGlobalMessage(false);
	    message.setCreatedByAdmin(senderUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")));

	    Message savedMessage = messageRepository.save(message);

	    long unreadCount = messageRepository.countUnreadMessagesByReceiverId(receiverUser.getId());
	    
	    messagingTemplate.convertAndSendToUser(
	        receiverUser.getId().toString(),
	        "/queue/unreadMessages",
	        unreadCount
	    );

	    return message.getId();
	}
	
	public long countUnreadMessages(int receiverId) {
        return messageRepository.countUnreadMessagesByReceiverId(receiverId);
    }
	
	private Pageable createPageable(int page, int size) {
	    return PageRequest.of(page, size, Sort.by("createdDate").descending());
	}
	
	private PageResponse<MessageResponse> mapToPageResponse(Page<Message> messages, int page, int size) {
	    List<MessageResponse> messageResponses = messages.stream()
	        .map(message -> {
	            return messageMapper.toMessageResponse(message);
	        })
	        .collect(Collectors.toList());
	    
	    return new PageResponse<>(
	        messageResponses,
	        messages.getNumber(),
	        messages.getSize(),
	        messages.getTotalElements(),
	        messages.getTotalPages(),
	        messages.isFirst(),
	        messages.isLast()
	    );
	}

}
