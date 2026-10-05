import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BASE_URL } from 'src/app/common/utilities';
import { QuestionRequest } from '../model/question-request.model';
import { QuestionResponse } from '../model/question-response.model';
import { PageResponse } from '../model/page-response.model';

@Injectable({
  providedIn: 'root',
})
export class QuestionService {
  
  constructor(private http: HttpClient) {}

  getAllQuestions(page: number, size: number): Observable<PageResponse<QuestionResponse>> {
    return this.http.get<PageResponse<QuestionResponse>>(`${BASE_URL}/questions?page=${page}&size=${size}`);
  }

  getQuestionById(questionId: number): Observable<QuestionResponse> {
    return this.http.get<QuestionResponse>(`${BASE_URL}/questions/${questionId}`);
  }

  getAllQuestionsByOwner(page: number, size: number): Observable<PageResponse<QuestionResponse>> {
    return this.http.get<PageResponse<QuestionResponse>>(`${BASE_URL}/questions/owner?page=${page}&size=${size}`);
  }

  getQuestionsForCategory(mainCategory: string, subCategory: string, page: number, size: number): Observable<PageResponse<QuestionResponse>> {
    return this.http.get<PageResponse<QuestionResponse>>(`${BASE_URL}/questions/${mainCategory}/${subCategory}?page=${page}&size=${size}`);
  }

  getQuestionsBySearchText(searchText: string, page: number, size: number): Observable<PageResponse<QuestionResponse>> {
    return this.http.get<PageResponse<QuestionResponse>>(`${BASE_URL}/questions/search/${searchText}?page=${page}&size=${size}`);
  }

  createQuestion(question: QuestionRequest): Observable<number> {
    return this.http.post<number>(`${BASE_URL}/questions`, question);
  }
}
