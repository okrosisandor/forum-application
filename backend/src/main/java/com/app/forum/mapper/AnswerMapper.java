package com.app.forum.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.forum.dto.AnswerRequest;
import com.app.forum.dto.AnswerResponse;
import com.app.forum.entity.Answer;
import com.app.forum.enums.VoteType;
import com.app.forum.repository.RatingRepository;
import com.app.forum.repository.UserRepository;

@Service
public class AnswerMapper {
	
	@Autowired
	private RatingMapper ratingMapper;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private UserRepository userRepository;

	public Answer toAnswer(AnswerRequest answerRequest) {
		return Answer.builder()
			.content(answerRequest.getContent())
			.build();
	}
	
	public AnswerResponse toAnswerResponse(Answer answer) {
		return AnswerResponse.builder()
			.answerId(answer.getId())
			.userId(answer.getUser().getId())
			.createdUser(answer.getUser().getDisplayName())
			.questionId(answer.getQuestion().getId())
			.content(answer.getContent())
			.createdDate(answer.getCreatedDate())
			.upvoteNumber(ratingRepository.countByAnswerIdAndVoteType(answer.getId(), VoteType.UPVOTE))
			.downvoteNumber(ratingRepository.countByAnswerIdAndVoteType(answer.getId(), VoteType.DOWNVOTE))
			.userRating(userRepository.calculateUserRating(answer.getUser().getId()))
			.userHasVotes(userRepository.hasReceivedAnyVotes(answer.getUser().getId()))
			.ratings(
                answer.getRatings().stream()
                    .map(ratingMapper::toRatingResponse)
                    .collect(Collectors.toList())
            )
			.reported(answer.isReported())
			.firstReported(answer.getFirstReported())
			.lastReported(answer.getLastReported())
			.reportedNumber(answer.getReportedNumber())
			.build();
	}
}
