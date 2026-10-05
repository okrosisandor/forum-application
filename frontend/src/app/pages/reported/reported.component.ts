import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { formatDate } from 'src/app/common/utilities';
import { AnswerResponse } from 'src/app/model/answer-response.model';
import { PageResponse } from 'src/app/model/page-response.model';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-reported',
  templateUrl: './reported.component.html',
  styleUrls: ['./reported.component.css']
})
export class ReportedComponent implements OnInit{

  @ViewChild('scrollableContent') scrollableContent!: ElementRef;

  answersPage!: PageResponse<AnswerResponse>;
  currentPage: number = 0;
  pageSize: number = 10;

  constructor(private adminService: AdminService){}

  ngOnInit(): void {
    this.loadReportedAnswers();
  }

  loadReportedAnswers(){
    this.adminService.findAllReportedAnswers(this.currentPage, this.pageSize).subscribe({
      next: (response: PageResponse<AnswerResponse>) => {
        this.answersPage = response;
        this.scrollToTop();
      },
      error: (error) => {
        console.error('Failed to fetch answers:', error);
      }
    });
  }

  scrollToTop(): void {
    if (this.scrollableContent) {
      this.scrollableContent.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  }

  formattedDate(date: string): string {
      return formatDate(date);
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

  onPageChanged(page: number): void {
    this.currentPage = page;
    this.loadReportedAnswers();
  }

}
