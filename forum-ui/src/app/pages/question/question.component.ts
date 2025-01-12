import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { formatDate } from 'src/app/common/utilities';
import { PageResponse } from 'src/app/model/page-response.model';
import { QuestionResponse } from 'src/app/model/question-response.model';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  @ViewChild('scrollableContent') scrollableContent!: ElementRef;

  questionsPage!: PageResponse<QuestionResponse>;
  currentPage: number = 0;
  pageSize = 10;
  mainCategory!: string;
  subCategory!: string;
  scenario!: string;
  searchText: string = '';

  constructor(
    private questionService: QuestionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.mainCategory = params.get('mainCategory') || '';
      this.subCategory = params.get('subCategory') || '';
      this.scenario = this.route.snapshot.queryParamMap.get('scenario') || '';
      this.searchText = this.route.snapshot.queryParamMap.get('searchText') || '';
      this.currentPage = +(this.route.snapshot.queryParamMap.get('page') || 0);

      this.loadQuestions();
    });

    this.route.queryParamMap.subscribe(queryParams => {
      this.searchText = queryParams.get('searchText') || '';
      this.currentPage = +(queryParams.get('page') || 0);

      this.loadQuestions();
    });
  }

  submitSearch(): void {
    if (!this.searchText.trim()) {
      this.getAllQuestions();
    } else {
      this.searchQuestions(this.searchText);
    }
  }

  loadQuestions(): void {
    if (this.searchText.trim()) {
      this.searchQuestions(this.searchText);
    } else {
      if (this.scenario === 'category') {
        this.getCategoryQuestions();
      } else if (this.scenario === 'owner') {
        this.getOwnerQuestions();
      } else {
        this.getAllQuestions();
      }
    }

  }

  private getAllQuestions(): void {
    this.questionService.getAllQuestions(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.updateQuestionsPage(response)
        this.scrollToTop();
      }, 
      error: error => console.error('Error fetching all questions:', error)
    });
  }

  private searchQuestions(searchText: string): void {
    this.questionService.getQuestionsBySearchText(searchText, this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.updateQuestionsPage(response)
      },
      error: error => console.error('Error fetching questions by search:', error)
    });
  }

  private getCategoryQuestions(): void {
    this.questionService.getQuestionsForCategory(this.mainCategory, this.subCategory, this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.updateQuestionsPage(response)
      },
      error: error => console.error('Error fetching questions for category:', error)
    });
  }

  private getOwnerQuestions(): void {
    this.questionService.getAllQuestionsByOwner(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.updateQuestionsPage(response)
      },
      error: error => console.error('Error fetching questions for owner:', error)
    });
  }

  private updateQuestionsPage(response: PageResponse<QuestionResponse>): void {
    this.questionsPage = response;
    this.scrollToTop();
    this.updateQueryParams();
  }

  scrollToTop(): void {
    if (this.scrollableContent) {
      this.scrollableContent.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  }

  formattedDate(date: string | null): string {
    return formatDate(date);
  }

  onPageChanged(page: number): void {
    this.currentPage = page;
    this.loadQuestions();
  }

  private updateQueryParams(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        searchText: this.searchText,
        page: this.currentPage,
        scenario: this.scenario
      },
      queryParamsHandling: 'merge'
    });
  }
}
