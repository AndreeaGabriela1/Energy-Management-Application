import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  // private baseUrl_user = "http://localhost:8090/user/register";
  ///private baseUrl_allUsers = "http://users.localhost/user/showallusers"; // Endpoint pentru obținerea tuturor utilizatorilor
  private baseUrl_user = "http://users.localhost:8086/user/register";
  private baseUrl_allUsers = "http://users.localhost:8086/user/showallusers"; // Endpoint pentru obținerea tuturor utilizatorilor
  constructor(private httpClient: HttpClient) { }

  public addUser(user: User): Observable<User>{
    console.log(user);
    return this.httpClient.post<User>(`${this.baseUrl_user}`, user);
  }
  public getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.baseUrl_allUsers);
  }
}
