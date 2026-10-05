import { Component, OnInit } from '@angular/core';
import { MainCategory } from 'src/app/enum/main-category';
import { CategoryResponse } from 'src/app/model/category-response.model';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit{

  categories: CategoryResponse[] = [];
  menuExpanded: boolean = true;

  constructor(private categoryService: CategoryService){}

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (response) => {
        this.categories = response;
      },
      error: (error) => {
        console.error('Categories failed:', error);
      },
    });
  }

  toggleCategory(categoryName: MainCategory): void {
    const category = this.categories.find(cat => cat.mainCategory === categoryName);
    if (category) {
        category.expanded = !category.expanded;
    }
  }
}
