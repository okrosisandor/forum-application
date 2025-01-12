package com.app.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.forum.entity.Rating;
import com.app.forum.enums.VoteType;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

	@Query("SELECT r FROM Rating r WHERE r.answer.id = :answerId")
	List<Rating> findAllRatingsByAnswerId(int answerId);

	int countByAnswerIdAndVoteType(Integer answerId, VoteType voteType);

}
