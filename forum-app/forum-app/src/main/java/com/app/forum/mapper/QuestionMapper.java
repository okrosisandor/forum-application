package com.app.forum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.forum.dto.QuestionRequest;
import com.app.forum.dto.QuestionResponse;
import com.app.forum.entity.Question;
import com.app.forum.repository.AnswerRepository;

@Service
public class QuestionMapper {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public Question toQuestion(QuestionRequest questionRequest) {
		return Question.builder()
	        .mainCategory(questionRequest.getMainCategory())
	        .subCategory(questionRequest.getSubCategory())
	        .title(questionRequest.getTitle())
	        .description(questionRequest.getDescription())
	        .build();
	}
	
	public QuestionResponse toQuestionResponse(Question question) {
	    return QuestionResponse.builder()
	        .questionId(question.getId())
	        .userId(question.getUser().getId())
	        .createdUser(question.getUser().getUsername())
	        .mainCategory(question.getMainCategory())
	        .subCategory(question.getSubCategory())
	        .title(question.getTitle())
	        .description(question.getDescription())
	        .createdDate(question.getCreatedDate())
	        .totalAnswers(answerRepository.countAnswersByQuestionId(question.getId()))
	        .latestAnswerDate(answerRepository.getLatestAnswerDate(question.getId()))
	        .build();
	}
}
