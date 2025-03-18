import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterUserService {
  private apiUrl = 'http://users.localhost:8086/user/register';
  // private apiUrl = 'http://users.localhost/user/register';
  constructor(private http: HttpClient) {}

  register(userData: any): Observable<any> {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Access-Control-Allow-Origin', '*');

    return this.http.post(this.apiUrl, userData, { headers: headers }).pipe(
      tap((response: any) => {
        // Stocăm token-ul sau alte informații relevante, dacă sunt necesare
        if (response.accessToken) {
          localStorage.setItem('accessToken', response.accessToken);
          localStorage.setItem('userName', response.user.name);
          localStorage.setItem('userRole', response.user.roles);
        }
      })
    );
  }
}
