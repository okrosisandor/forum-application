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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Categories", description = "Endpoints for retrieving discussion categories and their hierarchical structures")
public class CategoryController {

	private final CategoryService categoryService;
	
	@GetMapping("")
    @Operation(summary = "Get all categories", description = "Retrieves a complete list of all main forum categories along with their associated subcategories.")
    public ResponseEntity<List<CategoryResponse>> getAllCategoriesWithSubcategories() {
        return ResponseEntity.ok(categoryService.getAllCategoriesWithSubcategories());
    }
	
	@GetMapping("/{mainCategory}/subcategories")
    @Operation(summary = "Get subcategories by main category", description = "Retrieves all valid subcategories under a specific main category type.")
    public ResponseEntity<List<SubCategoryType>> getSubcategories(@PathVariable MainCategoryType mainCategory) {
        return ResponseEntity.ok(categoryService.getSubcategories(mainCategory));
    }
}