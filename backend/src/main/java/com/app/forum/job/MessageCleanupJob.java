package com.app.forum.job;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.forum.repository.MessageRepository;

@Service
public class MessageCleanupJob {
	
	@Autowired
	private MessageRepository messageRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(MessageCleanupJob.class);
	
	@Scheduled(cron = "0 */15 * * * *")
    @Transactional
    public void deleteExpiredMessages() {
        LocalDateTime expirationTime = LocalDateTime.now().minusWeeks(2);
        logger.info("Scheduled task triggered at: " + LocalDateTime.now());
        messageRepository.deleteByCreatedDateBefore(expirationTime);
        logger.info("Messages older than 2 weeks are deleted.");
    }
}
