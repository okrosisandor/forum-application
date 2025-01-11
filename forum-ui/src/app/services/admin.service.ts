import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AnswerResponse } from '../model/answer-response.model';
import { BASE_URL } from '../common/utilities';
import { Observable } from 'rxjs';
import { PageResponse } from '../model/page-response.model';
import { GlobalMessageRequest } from '../model/global-message-request';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  findAllReportedAnswers(page: number, size: number): Observable<PageResponse<AnswerResponse>> {
    return this.http.get<PageResponse<AnswerResponse>>(
      `${BASE_URL}/admin/reported?page=${page}&size=${size}`
    );
  }

  cancelReportedStatus(answerId: number): Observable<number> {
    return this.http.get<number>(`${BASE_URL}/admin/cancel-reported/${answerId}`);
  }

  createGlobalMessage(message: GlobalMessageRequest): Observable<number> {
    return this.http.post<number>(`${BASE_URL}/admin/global-message`, message);
  }

  deleteAnswer(answerId: number): Observable<number> {
    return this.http.delete<number>(`${BASE_URL}/admin/${answerId}`);
  }
}
