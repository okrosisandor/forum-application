import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AnswerRequest } from '../model/answer-request.model';
import { BASE_URL } from '../common/utilities';
import { Observable } from 'rxjs';
import { PageResponse } from '../model/page-response.model';
import { AnswerResponse } from '../model/answer-response.model';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient) { }

  getAnswersByQuestionId(questionId: number, page: number, size: number): Observable<PageResponse<AnswerResponse>> {
    return this.http.get<PageResponse<AnswerResponse>>(
      `${BASE_URL}/answers/${questionId}?page=${page}&size=${size}`
    );
  }

  createAnswer(answer: AnswerRequest): Observable<number> {
    return this.http.post<number>(`${BASE_URL}/answers`, answer);
  }

  reportAnswer(answerId: number): Observable<any> {
    return this.http.get<any>(
      `${BASE_URL}/answers/report/${answerId}`
    );
  }
}
