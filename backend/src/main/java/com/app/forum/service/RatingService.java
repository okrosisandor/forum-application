package com.app.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.forum.dto.RatingRequest;
import com.app.forum.dto.RatingResponse;
import com.app.forum.entity.Answer;
import com.app.forum.entity.Rating;
import com.app.forum.entity.User;
import com.app.forum.mapper.RatingMapper;
import com.app.forum.repository.AnswerRepository;
import com.app.forum.repository.RatingRepository;
import com.app.forum.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class RatingService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private RatingMapper ratingMapper;
	
	@Autowired
	private RatingRepository ratingRepository;

	public Long createRating(@Valid RatingRequest ratingRequest) {
		User user = userRepository.findById(ratingRequest.getUserId())
        		.orElseThrow(() -> new EntityNotFoundException("User with ID " + ratingRequest.getUserId() + " not found"));
		
		Answer answer = answerRepository.findById(ratingRequest.getAnswerId())
        		.orElseThrow(() -> new EntityNotFoundException("Answer with ID " + ratingRequest.getAnswerId() + " not found"));
        
        Rating rating = ratingMapper.toRating(ratingRequest);
        rating.setUser(user);
        rating.setAnswer(answer);
        
        return ratingRepository.save(rating).getId();
	}
	
	public List<RatingResponse> findAllRatingsByAnswerId(int answerId){
		List<Rating> ratings = ratingRepository.findAllRatingsByAnswerId(answerId);
		
		return ratings.stream()
    		.map(ratingMapper::toRatingResponse)
    		.collect(Collectors.toList());
	}

}
