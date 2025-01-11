package com.app.forum.dto;

import java.time.LocalDateTime;

import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionResponse {
	
	private Integer questionId;
	
    private Integer userId;
    
    private String createdUser;
    
    private MainCategoryType mainCategory;
    
    private SubCategoryType subCategory;
    
    private String title;
    
    private String description;
    
    private LocalDateTime createdDate;
    
    private Integer totalAnswers;
    
    private LocalDateTime latestAnswerDate;
}
