package com.app.forum.dto;

import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionRequest {
	
    private Integer userId;
    
    @NotNull(message = "Main Category is required")
    private MainCategoryType mainCategory;
    
    @NotNull(message = "Subcategory is required")
    private SubCategoryType subCategory;
    
    @NotBlank(message = "Title is required")
    @Size(min = 10, max = 50, message = "Title should be between 10 and 50 characters.")
    private String title;
    
    @Size(min = 10, max = 5000, message = "Description should be between 10 and 5000 characters.")
    @NotBlank(message = "Description is required")
    private String description;

}
