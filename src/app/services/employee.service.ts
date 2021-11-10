import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Employee } from '../models/employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService
{
  private apiServerUrl = environment.apiBaseUrl;
  private token = "Bearer " + localStorage.getItem('jwt');

  constructor(private http: HttpClient) { }


  public getEmployees(): Observable<Employee[]>
  {
    return this.http.get<Employee[]>(`${this.apiServerUrl}/employees`, {headers: new HttpHeaders({'Authorization': this.token})});
  }

  public getSingleEmployee(id: number): Observable<Employee>
  {
    return this.http.get<Employee>(`${this.apiServerUrl}/employee/find/`+ id, {headers: new HttpHeaders({'Authorization': this.token})});
  }

  public addEmployee(employee: Employee): Observable<Employee>
  {
    return this.http.post<Employee>(`${this.apiServerUrl}/employee/add`, employee, {headers: new HttpHeaders({'Authorization': this.token})});
  }

  public updateEmployee(employee: Employee): Observable<Employee>
  {
    return this.http.put<Employee>(`${this.apiServerUrl}/employee/update`, employee, {headers: new HttpHeaders({'Authorization': this.token})});
  }

  public deleteEmployee(employeeId: number | undefined): Observable<void>
  {
    return this.http.delete<void>(`${this.apiServerUrl}/employee/delete/${employeeId}`, {headers: new HttpHeaders({'Authorization': this.token})});
  }

}
