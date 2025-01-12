import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CategoryResponse } from '../model/category-response.model';
import { map, Observable } from 'rxjs';
import { BASE_URL } from 'src/app/common/utilities';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  
  constructor(private http: HttpClient) { }

  getAllCategories(): Observable<CategoryResponse[]> {
    return this.http.get<CategoryResponse[]>(`${BASE_URL}/categories`).pipe(
      map(categories => categories.map(category => ({
          ...category,
          expanded: false
      })))
    );
  }
}
