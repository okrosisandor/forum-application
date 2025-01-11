import { Injectable } from '@angular/core';
import { BASE_URL } from '../common/utilities';
import { PageResponse } from '../model/page-response.model';
import { Observable } from 'rxjs';
import { GlobalMessageResponse } from '../model/global-message-response';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) { }

  getMessagesBySender(page: number, size: number): Observable<PageResponse<GlobalMessageResponse>> {
      return this.http.get<PageResponse<GlobalMessageResponse>>(
        `${BASE_URL}/notifications/sender?page=${page}&size=${size}`
      );
    }
}
