import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Login } from '../models/login.model';
import { AuthenticationResponse } from '../models/authenticationResponse.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiServerUrl = environment.apiBaseUrl;
  private token = "Bearer " + localStorage.getItem('jwt');
  private isloggedIn!: boolean;

  constructor(private http: HttpClient) { }


  public getUsers(): Observable<User[]>
  {
    return this.http.get<User[]>(`${this.apiServerUrl}/users`,{headers: new HttpHeaders({'Authorization': this.token})});
  }

  public getSingleEmployee(id: number): Observable<User>
  {
    return this.http.get<User>(`${this.apiServerUrl}/user/find/`+ id);
  }

  public addEmployee(user: User): Observable<User>
  {
    return this.http.post<User>(`${this.apiServerUrl}/user/add`, user);
  }

  public updateEmployee(user: User): Observable<User>
  {
    return this.http.put<User>(`${this.apiServerUrl}/user/update`, user);
  }

  public deleteEmployee(userId: number | undefined): Observable<void>
  {
    return this.http.delete<void>(`${this.apiServerUrl}/user/delete/${userId}`);
  }

  public authentication(login : Login): Observable<AuthenticationResponse>
  {
    return this.http.post<AuthenticationResponse>(`${this.apiServerUrl}/auth/login`, login);
  }

  public saveToken(jwt:string) {
    localStorage.setItem('jwt', jwt);
    this.token = jwt;
    this.isloggedIn = true;
  }

}
