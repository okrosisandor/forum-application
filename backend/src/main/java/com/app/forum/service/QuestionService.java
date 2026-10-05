package com.app.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.forum.common.PageResponse;
import com.app.forum.dto.QuestionRequest;
import com.app.forum.dto.QuestionResponse;
import com.app.forum.entity.Question;
import com.app.forum.entity.User;
import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;
import com.app.forum.exception.QuestionNotFoundException;
import com.app.forum.mapper.QuestionMapper;
import com.app.forum.repository.QuestionRepository;
import com.app.forum.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	public Integer createQuestion(QuestionRequest questionRequest) {
        User user = userRepository.findById(questionRequest.getUserId())
        		.orElseThrow(() -> new EntityNotFoundException("User with ID " + questionRequest.getUserId() + " not found"));
        
        Question question = questionMapper.toQuestion(questionRequest);
        question.setUser(user);
        
        return questionRepository.save(question).getId();
    }
	
	public QuestionResponse getQuestionById(int questionId) {
	    Question question = questionRepository.findById(questionId)
	        .orElseThrow(() -> new QuestionNotFoundException("Question with ID " + questionId + " not found"));

	    return questionMapper.toQuestionResponse(question);
	}
	
	public PageResponse<QuestionResponse> findAllQuestions(int page, int size) {
	    Pageable pageable = createPageable(page, size);
	    Page<Question> questions = questionRepository.findAll(pageable);
	    return mapToPageResponse(questions, page, size);
	}

	public PageResponse<QuestionResponse> findAllQuestionsByOwner(int page, int size, Authentication theUser) {
	    User user = (User) theUser.getPrincipal();
	    Pageable pageable = createPageable(page, size);
	    Page<Question> questions = questionRepository.findAllByOwner(pageable, user.getId());
	    return mapToPageResponse(questions, page, size);
	}
	
	public PageResponse<QuestionResponse> findAllQuestionsForSubcategory(MainCategoryType mainCategory, SubCategoryType subCategory, int page,
			int size) {
		Pageable pageable = createPageable(page, size);
	    Page<Question> questions = questionRepository.findAllForSubCategory(pageable, mainCategory, subCategory);
	    return mapToPageResponse(questions, page, size);
	}
	
	public PageResponse<QuestionResponse> findAllQuestionsBySearchText(String searchText, int page, int size) {
		Pageable pageable = createPageable(page, size);
		Page<Question> questions = questionRepository.findAllBySearchText(pageable, searchText);
	    return mapToPageResponse(questions, page, size);
	}

	private Pageable createPageable(int page, int size) {
	    return PageRequest.of(page, size, Sort.by("createdDate").descending());
	}
	
	private PageResponse<QuestionResponse> mapToPageResponse(Page<Question> questions, int page, int size) {
	    List<QuestionResponse> questionResponses = questions.stream()
	        .map(question -> {
	            return questionMapper.toQuestionResponse(question);
	        })
	        .collect(Collectors.toList());
	    
	    return new PageResponse<>(
	        questionResponses,
	        questions.getNumber(),
	        questions.getSize(),
	        questions.getTotalElements(),
	        questions.getTotalPages(),
	        questions.isFirst(),
	        questions.isLast()
	    );
	}

}
