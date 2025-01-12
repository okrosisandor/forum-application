import { Injectable } from '@angular/core';
import { UserUpdateRequest } from '../model/user/user-update-request.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { BASE_URL } from '../common/utilities';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  updateUser(updatedUser: UserUpdateRequest, userId: number): Observable<any> {
    return this.http.patch<any>(`${BASE_URL}/users/${userId}`, updatedUser);
  }
}
