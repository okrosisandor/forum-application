import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';
import { WebSocketService } from 'src/app/services/websocket.service';

@Component({
  selector: 'app-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css']
})
export class UserMenuComponent implements OnInit{

  expanded: boolean = true;
  unreadMessageCount: number = 0;

  constructor(
    private tokenService: TokenService,
    private webSocketService: WebSocketService,
    private router: Router
  ) {}

  logout() {
    this.tokenService.logout();
    this.router.navigate(['/login']);
  }

  ngOnInit(): void {
    this.webSocketService.unreadMessageCount$.subscribe((count) => {
      this.unreadMessageCount = count;
    });
  }
}
