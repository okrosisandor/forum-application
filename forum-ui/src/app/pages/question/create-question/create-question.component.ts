import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MainCategory } from 'src/app/enum/main-category';
import { SubCategory } from 'src/app/enum/subcategory';
import { CategoryResponse } from 'src/app/model/category-response.model';
import { QuestionRequest } from 'src/app/model/question-request.model';
import { CategoryService } from 'src/app/services/category.service';
import { QuestionService } from 'src/app/services/question.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.css']
})
export class CreateQuestionComponent implements OnInit {

  question: QuestionRequest = {
    userId: this.tokenService.user.userId,
    mainCategory: null,
    subCategory: null,
    title: "",
    description: ""
  };

  categories: CategoryResponse[] = [];
  filteredSubCategories: SubCategory[] = [];

  errorMsg: Array<string> = [];

  constructor(
    private questionService: QuestionService,
    private categoryService: CategoryService,
    private tokenService: TokenService,
    private router: Router,
  ){}

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

  onMainCategoryChange(selectedMainCategory: MainCategory): void {
    const selectedCategory = this.categories.find(
      (category) => category.mainCategory === selectedMainCategory
    );
    this.filteredSubCategories = selectedCategory ? selectedCategory.subcategories : [];
    this.question.subCategory = null;
  }

  create() {
    this.errorMsg = [];

    this.questionService.createQuestion(this.question).subscribe({
      next: (questionId: number) => {
        this.router.navigate(['/question', questionId, 'details']);
      },
      error: (error) => {
        if (error.error.validationErrors) {
          this.errorMsg = error.error.validationErrors;
        } else {
          this.errorMsg.push(error.error.error)
        }
      },
    });
  }
}
