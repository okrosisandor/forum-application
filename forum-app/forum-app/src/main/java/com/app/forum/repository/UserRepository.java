package com.app.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.forum.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT CASE " +
	           "WHEN (COUNT(CASE WHEN r.voteType = 'UPVOTE' THEN 1 END) + COUNT(CASE WHEN r.voteType = 'DOWNVOTE' THEN 1 END)) = 0 THEN 0 " + 
	           "ELSE ROUND((COUNT(CASE WHEN r.voteType = 'UPVOTE' THEN 1 END) * 100.0) / " +
	           "(COUNT(CASE WHEN r.voteType = 'UPVOTE' THEN 1 END) + COUNT(CASE WHEN r.voteType = 'DOWNVOTE' THEN 1 END)), 0) " +
	           "END " +
	           "FROM Rating r " +
	           "JOIN r.answer a " +
	           "WHERE a.user.id = :userId")
    Integer calculateUserRating(@Param("userId") Integer userId);
	
	@Query("SELECT COUNT(r) > 0 FROM Rating r " +
	           "JOIN r.answer a " +
	           "WHERE a.user.id = :userId")
    boolean hasReceivedAnyVotes(@Param("userId") Integer userId);

	Optional<User> findByUsername(String receiverUserName);
	
	Optional<User> findByDisplayName(String receiverUserName);
	
}
