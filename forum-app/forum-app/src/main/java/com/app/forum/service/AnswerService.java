package com.app.forum.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.AnswerRequest;
import com.app.forum.dto.AnswerResponse;
import com.app.forum.entity.Answer;
import com.app.forum.entity.Question;
import com.app.forum.entity.User;
import com.app.forum.mapper.AnswerMapper;
import com.app.forum.repository.AnswerRepository;
import com.app.forum.repository.QuestionRepository;
import com.app.forum.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AnswerService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerMapper answerMapper;
	
	@Autowired
	private AnswerRepository answerRepository;

	public Integer createAnswer(AnswerRequest answerRequest) {
		
		User user = userRepository.findById(answerRequest.getUserId())
        		.orElseThrow(() -> new EntityNotFoundException("User with ID " + answerRequest.getUserId() + " not found"));
		
		Question question = questionRepository.findById(answerRequest.getQuestionId())
        		.orElseThrow(() -> new EntityNotFoundException("Question with ID " + answerRequest.getQuestionId() + " not found"));
		
		Answer answer = answerMapper.toAnswer(answerRequest);
		answer.setUser(user);
		answer.setQuestion(question);
		answer.setReported(false);
		answer.setReportedNumber(0);
		
		return answerRepository.save(answer).getId();
	}

	public PageResponse<AnswerResponse> findAllAnswersByQuestionId(int questionId, int page, int size) {
		Pageable pageable = createPageable(page, size);
		Page<Answer> answers = answerRepository.findAllByQuestionId(questionId, pageable);
		return mapToPageResponse(answers, page, size);
	}
	
	private Pageable createPageable(int page, int size) {
	    return PageRequest.of(page, size, Sort.by("createdDate").descending());
	}
	
	private PageResponse<AnswerResponse> mapToPageResponse(Page<Answer> answers, int page, int size) {
	    List<AnswerResponse> answerResponses = answers.stream()
	        .map(answer -> {
	            return answerMapper.toAnswerResponse(answer);
	        })
	        .collect(Collectors.toList());
	    
	    return new PageResponse<>(
	        answerResponses,
	        answers.getNumber(),
	        answers.getSize(),
	        answers.getTotalElements(),
	        answers.getTotalPages(),
	        answers.isFirst(),
	        answers.isLast()
	    );
	}
	
	public Integer reportAnswer(int answerId, Principal loggedInUser) {
		
		User user = userRepository.findByEmail(loggedInUser.getName())
        		.orElseThrow(() -> new EntityNotFoundException("User with email " + loggedInUser.getName() + " not found"));
		
		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new EntityNotFoundException("Answer with ID " + answerId + " not found"));
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		if (!answer.isReported()) {
			answer.setReported(true);
			answer.setFirstReported(currentTime);
			answer.setLastReported(currentTime);
		} else {
			answer.setLastReported(currentTime);
		}
		
		answer.setReportedNumber(answer.getReportedNumber() + 1);
		
		return answerRepository.save(answer).getId();
	}
	
	public PageResponse<AnswerResponse> findAllReportedAnswers(int page, int size) {
		Pageable pageable = createPageable(page, size);
		Page<Answer> answers = answerRepository.findAllReportedAnswers(pageable);
		return mapToPageResponse(answers, page, size);
	}
	
	public Integer cancelReportedStatus(int answerId) {
		
		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new EntityNotFoundException("Answer with ID " + answerId + " not found"));
		
		answer.setReported(false);
		answer.setFirstReported(null);
		answer.setLastReported(null);
		answer.setReportedNumber(0);
		
		return answerRepository.save(answer).getId();
	}
	
	public Integer deleteAnswer(int answerId) {
	    return answerRepository.findById(answerId)
            .map(answer -> {
                answerRepository.delete(answer);
                return answer.getId();
            })
            .orElseThrow(() -> new EntityNotFoundException("Answer with ID " + answerId + " not found"));
	}

}
