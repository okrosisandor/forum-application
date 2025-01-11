import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageResponse } from '../model/message-response.model';
import { PageResponse } from '../model/page-response.model';
import { Observable } from 'rxjs';
import { BASE_URL } from '../common/utilities';
import { MessageRequest } from '../model/message-request.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) { }

  getMessagesByReceiver(receiverId: number, page: number, size: number): Observable<PageResponse<MessageResponse>> {
    return this.http.get<PageResponse<MessageResponse>>(
      `${BASE_URL}/messages/${receiverId}?page=${page}&size=${size}`
    );
  }

  getMessagesBySender(senderId: number, page: number, size: number): Observable<PageResponse<MessageResponse>> {
    return this.http.get<PageResponse<MessageResponse>>(
      `${BASE_URL}/messages/sender/${senderId}?page=${page}&size=${size}`
    );
  }

  createMessage(message: MessageRequest): Observable<number> {
    return this.http.post<number>(`${BASE_URL}/messages`, message);
  }

  getUnreadMessageCount(userId: number): Observable<number> {
    return this.http.get<number>(`${BASE_URL}/messages/unread-count?userId=${userId}`);
  }
}
