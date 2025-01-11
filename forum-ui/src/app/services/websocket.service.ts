import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { BehaviorSubject } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private client!: Client;
  public unreadMessageCount$: BehaviorSubject<number> = new BehaviorSubject(0);
  loggedInUser: string = this.tokenService.user.email;
  loggedInUserId: number = this.tokenService.user.userId;

  constructor(private tokenService: TokenService) {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection() {
    this.client = new Client({
      brokerURL: undefined,
      webSocketFactory: () => new SockJS('http://localhost:8086/api/ws'),
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
    });
  
    this.client.onConnect = (frame) => {
      const userId = this.loggedInUserId;
  
      this.client.subscribe(`/user/${userId}/queue/unreadMessages`, (message: IMessage) => {
        const unreadCount = JSON.parse(message.body);
        this.unreadMessageCount$.next(unreadCount);
      });
    };
  
    this.client.onStompError = (frame) => {
      console.error('Broker reported error: ', frame.headers['message']);
      console.error('Additional details: ', frame.body);
    };
  
    this.client.activate();
  }

  resetUnreadMessages(): void {
    this.unreadMessageCount$.next(0);
  }
  
}
