import { HttpErrorResponse } from '@angular/common/http';
import { Employee } from './../../models/employee.model';
import { EmployeeService } from './../../services/employee.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-single-employee',
  templateUrl: './single-employee.component.html',
  styleUrls: ['./single-employee.component.css']
})
export class SingleEmployeeComponent implements OnInit {

  employee!: Employee

  constructor(private employeeService: EmployeeService,
              private route : ActivatedRoute,
              private router : Router) { }

  ngOnInit(): void {
    this.employee = new Employee();
    const id = this.route.snapshot.params['id'];
    this.employeeService.getSingleEmployee(+id).subscribe(
      (response: Employee) => {
        this.employee = response;
        //console.log(this.employee);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  onBack(){
    this.router.navigate(['/employees']);
  }

}
