import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationRequest } from 'src/app/model/user/authentication-request.model';
import { RegistrationRequest } from 'src/app/model/registration-request.model';
import { BASE_URL } from 'src/app/common/utilities';
import { AuthenticationResponse } from 'src/app/model/user/authentication-response.model';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private tokenService: TokenService) {}

  authenticate(request: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${BASE_URL}/auth/login`, request);
  }

  register(request: RegistrationRequest): Observable<any> {
    return this.http.post<any>(`${BASE_URL}/auth/register`, request);
  }

}
