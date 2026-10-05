package com.app.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.forum.entity.Question;
import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	List<Question> findAll(Sort sort);

	@Query("SELECT q FROM Question q WHERE q.user.id = :id")
	Page<Question> findAllByOwner(Pageable pageable, Integer id);

	@Query("SELECT q FROM Question q WHERE q.mainCategory = :mainCategory AND q.subCategory = :subCategory")
	Page<Question> findAllForSubCategory(Pageable pageable, MainCategoryType mainCategory, SubCategoryType subCategory);

	@Query("SELECT DISTINCT q FROM Question q LEFT JOIN q.answers a " +
	           "WHERE LOWER(q.title) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
	           "OR LOWER(q.description) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
	           "OR LOWER(a.content) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Question> findAllBySearchText(Pageable pageable, @Param("searchText") String searchText);
}
