package com.app.forum.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.forum.dto.CategoryResponse;
import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;
import com.app.forum.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

	private final CategoryService categoryService;
	
	@GetMapping("")
    public ResponseEntity<List<CategoryResponse>> getAllCategoriesWithSubcategories() {
        return ResponseEntity.ok(categoryService.getAllCategoriesWithSubcategories());
    }
	
	@GetMapping("/{mainCategory}/subcategories")
    public ResponseEntity<List<SubCategoryType>> getSubcategories(@PathVariable MainCategoryType mainCategory) {
        return ResponseEntity.ok(categoryService.getSubcategories(mainCategory));
    }
}
