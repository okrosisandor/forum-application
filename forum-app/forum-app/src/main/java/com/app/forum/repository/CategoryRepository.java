package com.app.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.forum.entity.Category;
import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Optional<Category> findByMainCategory(MainCategoryType mainCategory);
	
	@Query("SELECT c FROM Category c JOIN c.subcategories s WHERE s = ?1")
	Optional<Category> findBySubcategoriesContaining(SubCategoryType subcategory);

}
