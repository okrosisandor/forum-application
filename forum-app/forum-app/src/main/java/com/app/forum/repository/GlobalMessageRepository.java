package com.app.forum.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.forum.entity.GlobalMessage;

@Repository
public interface GlobalMessageRepository extends JpaRepository<GlobalMessage, Integer> {
	
	@Query("SELECT m FROM GlobalMessage m order by m.createdDate desc")
	Page<GlobalMessage> findAllBySender(Pageable pageable);
	
	void deleteByCreatedDateBefore(LocalDateTime timestamp);

}
