package com.app.forum.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {
	
    private int id;
    
    private MainCategoryType mainCategory;
    
    private List<SubCategoryType> subcategories;
    
	private LocalDateTime createdDate;

}
