package com.app.forum.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.forum.dto.CategoryResponse;
import com.app.forum.entity.Category;
import com.app.forum.enums.MainCategoryType;
import com.app.forum.enums.SubCategoryType;
import com.app.forum.mapper.CategoryMapper;
import com.app.forum.repository.CategoryRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
	
    @Autowired
	private CategoryMapper categoryMapper;

    private final Map<MainCategoryType, List<SubCategoryType>> defaultSubcategories;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

        defaultSubcategories = new LinkedHashMap<>();
        // Entertainment
        defaultSubcategories.put(MainCategoryType.ENTERTAINMENT, Arrays.asList(
            SubCategoryType.MOVIES_AND_SERIES, SubCategoryType.MUSIC, SubCategoryType.VIDEO_GAMES,
            SubCategoryType.BOOKS_AND_COMICS, SubCategoryType.THEATER_AND_PERFORMING_ARTS,
            SubCategoryType.OTHER
        ));

        // Technology
        defaultSubcategories.put(MainCategoryType.TECHNOLOGY, Arrays.asList(
            SubCategoryType.PROGRAMMING, SubCategoryType.MOBILE_APPS, SubCategoryType.HARDWARE,
            SubCategoryType.CYBER_SECURITY, SubCategoryType.ARTIFICIAL_INTELLIGENCE,
            SubCategoryType.OTHER
        ));

        // Sports
        defaultSubcategories.put(MainCategoryType.SPORTS, Arrays.asList(
            SubCategoryType.FOOTBALL, SubCategoryType.BASKETBALL, SubCategoryType.EXTREME_SPORTS,
            SubCategoryType.ESPORTS, SubCategoryType.FITNESS_AND_BODYBUILDING,
            SubCategoryType.OTHER
        ));

        // Lifestyle
        defaultSubcategories.put(MainCategoryType.LIFESTYLE, Arrays.asList(
            SubCategoryType.HEALTHY_EATING, SubCategoryType.TRAVEL_AND_ADVENTURE,
            SubCategoryType.FASHION_AND_BEAUTY, SubCategoryType.HOME_GARDENING,
            SubCategoryType.RELATIONSHIPS_AND_FAMILY, SubCategoryType.OTHER
        ));

        // Sexuality
        defaultSubcategories.put(MainCategoryType.SEXUALITY, Arrays.asList(
            SubCategoryType.SEXUAL_HEALTH, SubCategoryType.RELATIONSHIPS_AND_DATING,
            SubCategoryType.SEXUAL_ORIENTATION, SubCategoryType.INTIMACY_AND_COMMUNICATION,
            SubCategoryType.SOCIAL_AND_CULTURAL_ISSUES, SubCategoryType.OTHER
        ));

        // Gastronomy
        defaultSubcategories.put(MainCategoryType.GASTRONOMY, Arrays.asList(
            SubCategoryType.FOOD_AND_RECIPES, SubCategoryType.RESTAURANTS_AND_CAFES,
            SubCategoryType.WINES_AND_BEERS, SubCategoryType.VEGETARIAN_AND_VEGAN_FOOD,
            SubCategoryType.WORLDWIDE_CUISINES, SubCategoryType.OTHER
        ));

        // Hobbies
        defaultSubcategories.put(MainCategoryType.HOBBIES, Arrays.asList(
            SubCategoryType.CRAFTING, SubCategoryType.MODEL_BUILDING, SubCategoryType.PHOTOGRAPHY,
            SubCategoryType.FURNITURE_RESTORATION, SubCategoryType.FISHING, SubCategoryType.OTHER
        ));

        // Finance
        defaultSubcategories.put(MainCategoryType.FINANCE, Arrays.asList(
            SubCategoryType.INVESTMENTS, SubCategoryType.CRYPTOCURRENCIES,
            SubCategoryType.PERSONAL_FINANCE, SubCategoryType.REAL_ESTATE_MARKET,
            SubCategoryType.BUSINESS_TRENDS, SubCategoryType.OTHER
        ));

        // Science
        defaultSubcategories.put(MainCategoryType.SCIENCE, Arrays.asList(
            SubCategoryType.SPACE_EXPLORATION, SubCategoryType.PHYSICS, SubCategoryType.BIOLOGY,
            SubCategoryType.CHEMISTRY, SubCategoryType.SCIENCE_NEWS, SubCategoryType.OTHER
        ));

        // Cars and Motorcycles
        defaultSubcategories.put(MainCategoryType.CARS_AND_MOTORCYCLES, Arrays.asList(
            SubCategoryType.CAR_RACING, SubCategoryType.ELECTRIC_VEHICLES,
            SubCategoryType.CLASSIC_CARS, SubCategoryType.MOTORBIKES,
            SubCategoryType.VEHICLE_MAINTENANCE_AND_TUNING, SubCategoryType.OTHER
        ));

        // Education and Career
        defaultSubcategories.put(MainCategoryType.EDUCATION_AND_CAREER, Arrays.asList(
            SubCategoryType.LANGUAGE_LEARNING, SubCategoryType.CAREER_ADVICE,
            SubCategoryType.ONLINE_COURSES, SubCategoryType.HIGHER_EDUCATION,
            SubCategoryType.CAREER_GUIDANCE, SubCategoryType.OTHER
        ));

        // Other
        defaultSubcategories.put(MainCategoryType.OTHER, Arrays.asList(SubCategoryType.OTHER));
    }

    @PostConstruct
    public void initializeCategories() {
        for (Map.Entry<MainCategoryType, List<SubCategoryType>> entry : defaultSubcategories.entrySet()) {
            MainCategoryType mainCategory = entry.getKey();
            List<SubCategoryType> subcategories = entry.getValue();

            if (!categoryRepository.findByMainCategory(mainCategory).isPresent()) {
                Category category = new Category();
                category.setMainCategory(mainCategory);
                category.setSubcategories(subcategories);
                category.setCreatedDate(LocalDateTime.now());
                category.setLastModifiedDate(LocalDateTime.now());
                
                categoryRepository.save(category);
            }
        }
    }

    public List<SubCategoryType> getSubcategories(MainCategoryType mainCategory) {
        return categoryRepository.findByMainCategory(mainCategory)
                .map(Category::getSubcategories)
                .orElse(Collections.emptyList());
    }

    public List<CategoryResponse> getAllCategoriesWithSubcategories() {
        List<Category> categories = categoryRepository.findAll();
        
        return categories.stream()
        		.map(categoryMapper::toCategoryResponse)
        		.collect(Collectors.toList());
    }
}
