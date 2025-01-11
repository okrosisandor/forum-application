import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserResponse } from 'src/app/model/user/user-response';

@Injectable({
  providedIn: 'root',
})
export class TokenService {

  private jwtHelper = new JwtHelperService();
  
  isTokenNotValid() {
    return !this.isTokenValid();
  }

  set user(user: UserResponse) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  get user() {
    return JSON.parse(localStorage.getItem('user') || '{}');
  }

  set token(token: string) {
    localStorage.setItem('token', token);
  }

  get token() {
    return (localStorage.getItem('token') as string) || '';
  }

  isTokenValid() {
    const token: string = this.token;
    if (!token) {
      return false;
    }
    const isTokenExpired = this.jwtHelper.isTokenExpired(token);
    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }
    return true;
  }

  getAuthorities(): string[] {
    const token: string = this.token;
    if (!token) {
      return [];
    }

    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken?.authorities || [];
  }

  hasAuthority(authority: string): boolean {
    const authorities = this.getAuthorities();
    return authorities.includes(authority);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
}
