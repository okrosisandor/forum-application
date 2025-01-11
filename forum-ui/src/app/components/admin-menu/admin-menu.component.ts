import { Component } from '@angular/core';
import { WebSocketService } from 'src/app/services/websocket.service';

@Component({
  selector: 'app-admin-menu',
  templateUrl: './admin-menu.component.html',
  styleUrls: ['./admin-menu.component.css']
})
export class AdminMenuComponent {
  expanded: boolean = true;
  unreadMessageCount: number = 0;

  constructor(
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.webSocketService.unreadMessageCount$.subscribe((count) => {
      this.unreadMessageCount = count;
    });
  }
}
