import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserRequest } from '../interface/signup-request.interface';
import { LoginResponse } from '../interface/login.interface';
import { UserResponse } from '../interface/signup-response.interface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private baseUrl = '/api/v1';

  signUp(user: UserRequest): Observable<UserResponse> {
    return this.http.post<UserResponse>(`${this.baseUrl}/signup/in`, user);
  }

  login(credentials: string): Observable<LoginResponse> {
    return this.http.post<any>(`${this.baseUrl}/login/in`, credentials);
  }
}
