import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/app/services/question.service';
import { formatDate } from 'src/app/common/utilities';
import { QuestionResponse } from 'src/app/model/question-response.model';
import { AnswerRequest } from 'src/app/model/answer-request.model';
import { TokenService } from 'src/app/services/token.service';
import { AnswerService } from 'src/app/services/answer.service';
import { PageResponse } from 'src/app/model/page-response.model';
import { AnswerResponse } from 'src/app/model/answer-response.model';
import { RatingService } from 'src/app/services/rating.service';
import { RatingRequest } from 'src/app/model/rating-request.model';
import { RatingEnum } from 'src/app/enum/rating';
import { AdminService } from 'src/app/services/admin.service';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationDialogService } from 'src/app/services/confirmation-dialog.service';

@Component({
  selector: 'app-question-detail',
  templateUrl: './question-detail.component.html',
  styleUrls: ['./question-detail.component.css']
})
export class QuestionDetailComponent implements OnInit {

  @ViewChild('scrollableContent') scrollableContent!: ElementRef;

  question!: QuestionResponse;
  answersPage!: PageResponse<AnswerResponse>;
  ratingRequest: RatingRequest | null = null;
  RatingEnum = RatingEnum;
  loggedInUserId: number =  this.tokenService.user.userId;
  currentPage: number = 0;
  pageSize: number = 10;
  isLoggedIn: boolean = this.tokenService.isTokenValid();

  answer: AnswerRequest = {
    userId: this.loggedInUserId,
    questionId: null,
    content: ""
  };

  errorMsg: Array<string> = [];

  constructor(
    private questionService: QuestionService,
    private tokenService: TokenService,
    private answerService: AnswerService,
    private ratingService: RatingService,
    private adminService: AdminService,
    private toastService: ToastrService,
    private confirmationDialogService: ConfirmationDialogService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.loadQuestion();
  }

  loadQuestion(){
    const questionId = this.route.snapshot.paramMap.get('id');
    if (questionId) {
       this.questionService.getQuestionById(+questionId).subscribe({
         next: (response) => {
           this.question = response;
           this.answer.questionId = this.question.questionId;
           this.loadAnswers();
         },
         error: (error) => {
           console.error('Question fetch failed:', error);
         }
       });
     }
  }

  loadAnswers(): void {
    if (this.question?.questionId) {
      this.answerService.getAnswersByQuestionId(this.question.questionId, this.currentPage, this.pageSize).subscribe({
        next: (response: PageResponse<AnswerResponse>) => {
          this.answersPage = response;
          this.scrollToTop();
        },
        error: (error) => {
          console.error('Failed to fetch answers:', error);
        }
      });
    }
  }

  formattedDate(date: string): string {
    return formatDate(date);
  }

  createAnswer(): void {
    this.errorMsg = [];

    this.answerService.createAnswer(this.answer).subscribe({
      next: () => {
        const totalAnswers = this.answersPage ? this.answersPage.totalElements + 1 : 1;
        this.currentPage = Math.ceil(totalAnswers / this.pageSize) - 1;
        this.loadAnswers();

        this.answer.content = '';
      },
      error: (error) => {
        if (error.error.validationErrors) {
          this.errorMsg = error.error.validationErrors;
        }
      }
    });
  }

  canRateAnswer(answer: AnswerResponse): boolean {
    if (answer.userId === this.loggedInUserId) {
      return false;
    }
    const userRating = answer.ratings.find(rating => rating.userId === this.loggedInUserId);
    return !userRating;
  }

  createRating(answerId: number, vote: RatingEnum) {
    this.ratingRequest = {userId: this.loggedInUserId, answerId: answerId, voteType: vote}
    this.ratingService.createRating(this.ratingRequest).subscribe({
      next: () => {
        this.loadAnswers();
      },
      error: (error) => {
        if (error.error.validationErrors) {
          this.errorMsg = error.error.validationErrors;
        }
      }
    });
  }

  getRatingClass(answer: any): string {
    if (!answer.userHasVotes) {
      return 'text-white'; 
    }
    if (answer.userRating === 0) {
      return 'text-danger'; 
    }
    if (answer.userRating >= 0 && answer.userRating <= 29) {
      return 'text-danger'; 
    }
    if (answer.userRating >= 30 && answer.userRating <= 59) {
      return 'text-warning'; 
    }
    if (answer.userRating >= 60 && answer.userRating <= 79) {
      return 'text-yellow'; 
    }
    return 'text-green'; 
  }

  reportAnswer(answer: AnswerResponse){
    this.confirmationDialogService.confirmAction(
      'Are you sure you want to report this answer?',
      'This action cannot be undone!',
      'Yes, report it!',
      () => {
        this.answerService.reportAnswer(answer.answerId).subscribe({
          next: () => {
            this.toastService.info("Successfully reported", "Success");
          },
          error: (error) => {
            if (error.error.validationErrors) {
              this.errorMsg = error.error.validationErrors;
            }
          }
        });
      }
    );
  }

  deleteAnswer(answer: AnswerResponse) {
    this.confirmationDialogService.confirmAction(
      'Are you sure you want to delete this answer?',
      'This action cannot be undone!',
      'Yes, delete it!',
      () => {
        this.adminService.deleteAnswer(answer.answerId).subscribe({
          next: () => {
            this.loadAnswers();
            this.toastService.info('Successfully deleted', 'Success');
          },
          error: (error) => {
            if (error.error.validationErrors) {
              console.error('Validation errors:', error.error.validationErrors);
            }
          },
        });
      }
    );
  }

  cancelReportedStatus(answer: AnswerResponse) {
    this.confirmationDialogService.confirmAction(
      'Are you sure you want to cancel reported status?',
      'This action cannot be undone!',
      'Yes, cancel it!',
      () => {
        this.adminService.cancelReportedStatus(answer.answerId).subscribe({
          next: () => {
            this.loadAnswers();
            this.toastService.info('Successfully cancelled', 'Success');
          },
          error: (error) => {
            if (error.error.validationErrors) {
              console.error('Validation errors:', error.error.validationErrors);
            }
          },
        });
      }
    );
  }

  get isAdmin(): boolean {
    return this.tokenService.hasAuthority("ROLE_ADMIN");
  }

  scrollToTop(): void {
    if (this.scrollableContent) {
      this.scrollableContent.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  }

  onPageChanged(page: number): void {
    this.currentPage = page;
    this.loadQuestion();
  }
}
