import { Injectable } from '@angular/core';
import { RatingRequest } from '../model/rating-request.model';
import { Observable } from 'rxjs';
import { RatingResponse } from '../model/rating.resonse.model';
import { HttpClient } from '@angular/common/http';
import { BASE_URL } from '../common/utilities';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  constructor(private http: HttpClient) { }

  findAllRatingsByAnswerId(answerId: number): Observable<RatingResponse[]>{
    return this.http.get<RatingResponse[]>(`${BASE_URL}/ratings/${answerId}`);
  }

  createRating(rating: RatingRequest): Observable<number> {
    return this.http.post<number>(`${BASE_URL}/ratings`, rating);
  }
}
