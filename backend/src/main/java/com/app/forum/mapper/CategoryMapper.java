package com.app.forum.mapper;

import org.springframework.stereotype.Service;

import com.app.forum.dto.CategoryResponse;
import com.app.forum.entity.Category;

@Service
public class CategoryMapper {

	public CategoryResponse toCategoryResponse(Category category) {
		return CategoryResponse.builder()
				.id(category.getId())
				.mainCategory(category.getMainCategory())
				.subcategories(category.getSubcategories())
				.createdDate(category.getCreatedDate())
				.build();
	}
}
