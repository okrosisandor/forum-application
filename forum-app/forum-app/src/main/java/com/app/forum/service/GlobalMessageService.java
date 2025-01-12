package com.app.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.GlobalMessageRequest;
import com.app.forum.dto.GlobalMessageResponse;
import com.app.forum.entity.GlobalMessage;
import com.app.forum.entity.User;
import com.app.forum.mapper.GlobalMessageMapper;
import com.app.forum.repository.GlobalMessageRepository;
import com.app.forum.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GlobalMessageService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GlobalMessageMapper globalMessageMapper;
	
	@Autowired
	private GlobalMessageRepository globalMessageRepository;

	public Integer createGlobalMessage(GlobalMessageRequest messageRequest) {
	    
	    User senderUser = userRepository.findByDisplayName(messageRequest.getFrom())
	            .orElseThrow(() -> new EntityNotFoundException("User with username " + messageRequest.getFrom() + " not found."));
	    
        GlobalMessage message = globalMessageMapper.toGlobalMessage(messageRequest);
        
        message.setUser(senderUser);
        message.setGlobalMessage(true);
        message.setCreatedByAdmin(senderUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")));
        
        globalMessageRepository.save(message);

	    return null;
	}
	
	public PageResponse<GlobalMessageResponse> findAllMessages(int page, int size) {
		Pageable pageable = createPageable(page, size);
		
		Page<GlobalMessage> messages = globalMessageRepository.findAllBySender(pageable);
		
		return mapToPageResponse(messages, page, size);
	}
	
	private Pageable createPageable(int page, int size) {
	    return PageRequest.of(page, size, Sort.by("createdDate").descending());
	}
	
	private PageResponse<GlobalMessageResponse> mapToPageResponse(Page<GlobalMessage> messages, int page, int size) {
	    List<GlobalMessageResponse> messageResponses = messages.stream()
	        .map(message -> {
	            return globalMessageMapper.toGlobalMessageResponse(message);
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
