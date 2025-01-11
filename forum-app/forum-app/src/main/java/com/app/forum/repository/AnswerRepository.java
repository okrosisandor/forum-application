package com.app.forum.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.forum.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	
	Page<Answer> findByQuestionId(Integer questionId, Pageable pageable);

	@Query("SELECT a FROM Answer a WHERE a.question.id = :questionId order by a.createdDate")
	Page<Answer> findAllByQuestionId(int questionId, Pageable pageable);
	
	@Query("SELECT COUNT(a) FROM Answer a WHERE a.question.id = :questionId")
	int countAnswersByQuestionId(@Param("questionId") int questionId);

	@Query("SELECT MAX(a.createdDate) FROM Answer a WHERE a.question.id = :questionId")
	LocalDateTime getLatestAnswerDate(@Param("questionId") int questionId);
	
	@Query("SELECT a FROM Answer a WHERE a.reported = true order by a.createdDate")
	Page<Answer> findAllReportedAnswers(Pageable pageable);
}
