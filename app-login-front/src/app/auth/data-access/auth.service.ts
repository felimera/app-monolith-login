import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interface/user.interface';
import { Login } from '../interface/login.interface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private baseUrl = '/api/v1/login';

  signUp(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/signup`, user);
  }

  login(credentials: string): Observable<Login> {
    return this.http.post<any>(`${this.baseUrl}/in`, credentials);
  }
}
