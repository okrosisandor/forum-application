import { Component, OnInit } from '@angular/core';
import { TokenService } from './services/token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'forum-ui';

  userId: number;
  unreadMessageCount: number = 0;

  constructor(private tokenService: TokenService) {
    this.userId = this.tokenService.user.userId;
  }

  ngOnInit(): void {

  }

  get isLoggedIn(): boolean {
    return !this.tokenService.isTokenNotValid();
  }

  get isAdmin(): boolean {
    return this.tokenService.hasAuthority("ROLE_ADMIN");
  }

  getUsername(): string {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    return user.displayName;
  }
  
}
