// import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { User } from './user';
// import { Observable, tap } from 'rxjs';
// import { LoginResponse } from './login-response';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class LoginUserService {
//   ///private baseUrl = "http://users.localhost/user/login";
//   private baseUrl = "http://users.localhost/user/login";
//   constructor(private httpClient: HttpClient) {
//
//   }
//
//   loginUser(user: User): Observable<LoginResponse> {
//     console.log(user);
//     const headers = new HttpHeaders()
//       .set('content-type', 'application/json')
//       .set('Access-Control-Allow-Origin', '*');
//     return this.httpClient.post(`${this.baseUrl}`, user, {
//       headers: headers
//     }).pipe(tap( (resp: any) => {
//                 localStorage.setItem('accessToken',  resp.accessToken);
//                 localStorage.setItem('userName', resp.user.name);
//                 localStorage.setItem('userRole', resp.user.roles);
//                 }));
//   }
// }
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from './login-response';

@Injectable({
  providedIn: 'root'
})
export class LoginUserService {
  private baseUrl = "http://users.localhost:8086/user/login";
  // private baseUrl = "http://localhost:8090/user/login";

  constructor(private httpClient: HttpClient) {}

  loginUser(user: User): Observable<LoginResponse> {
    console.log(user);

    // Set the necessary headers (without 'Access-Control-Allow-Origin')
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    // Send the POST request with the user data and headers
    return this.httpClient.post<LoginResponse>(`${this.baseUrl}`, user, {
      headers: headers
    }).pipe(tap((resp: LoginResponse) => {
      // Check if the response values are defined before saving them in localStorage
      if (resp.accessToken) {
        localStorage.setItem('accessToken', resp.accessToken);
      }

      if (resp.user && resp.user.name) {
        localStorage.setItem('userName', resp.user.name);
      }

      if (resp.user && resp.user.roles) {
        localStorage.setItem('userRole', resp.user.roles);
      }
    }));
  }
}

