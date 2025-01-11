package com.app.forum.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.forum.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("SELECT m FROM Message m WHERE m.user.id = :userId order by m.createdDate desc")
	Page<Message> findAllByReceiverId(int userId, Pageable pageable);
	
	@Query("SELECT m FROM Message m WHERE m.fromUsername = :fromUsername order by m.createdDate desc")
	Page<Message> findAllBySender(String fromUsername, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDATE Message m SET m.isRead = true WHERE m.user.id = :userId AND m.isRead = false")
	void markAllAsReadByReceiverId(@Param("userId") int userId);
	
	@Query("SELECT COUNT(m) FROM Message m WHERE m.user.id = :userId AND m.isRead = false")
	long countUnreadMessagesByReceiverId(@Param("userId") int userId);
	
	void deleteByCreatedDateBefore(LocalDateTime timestamp);

}
