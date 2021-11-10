import { Employee } from './../models/employee.model';
import { Mail } from './../models/mail.model';
import { MailService } from './../services/mail.service';
import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../services/employee.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { User } from '../models/user.model';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit {
  employees!: Employee[];
  editEmployee!: Employee | null;
  deleteEmployee!: Employee | null;
  senMailEmployee!: Employee | null;


  constructor(private employeeService: EmployeeService, private userService: UserService,private router : Router) {}

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees(): void {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response;
        console.log(this.employees);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  onAddEmployee(addForm: NgForm): void {
    document.getElementById("add-employee-form")?.click();
    this.employeeService.addEmployee(addForm.value).subscribe(
      (response: Employee) => {
        console.log(response)
        this.getEmployees();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  onUpdateEmployee(employee: Employee): void {
    this.employeeService.updateEmployee(employee).subscribe(
      (response: Employee) => {
        console.log(response)
        this.getEmployees();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        console.log("Your error: " + error );
      }
    );
  }




  onDeleteEmployee(employeeId: number | undefined): void {
    this.employeeService.deleteEmployee(employeeId).subscribe(
      (response: void) => {
        console.log(response)
        this.getEmployees();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        console.log("Your error: " + error );
      }
    );
  }

  onOpenModal(employee: Employee | null, mode: string): void {
    const button = document.createElement("button");
    const container = document.getElementById("main-container");

    button.type = "button";
    button.style.display = "none";
    button.setAttribute("data-toggle", "modal");

    switch(mode) {
      case 'add': button.setAttribute("data-target", "#addEmployeeModal");
        break;
      case 'edit':
            this.editEmployee = employee;
            button.setAttribute("data-target", "#updateEmployeeModal");
        break;
      default:
            this.deleteEmployee = employee;
            button.setAttribute("data-target", "#deleteEmployeeModal");
    }

    container?.appendChild(button);
    button.click();
  }

  onViewEmployee(id : number){
    this.router.navigate(['/employees', 'view', id]);
  }

  onSendMail(id: number) {
    this.router.navigate(['/employees', 'mail', id]);
  }


  /******************************* Autocomplete *******************************/
  keyword = 'username';

  data: any;
  errorMsg!: string;
  isLoadingResult!: boolean;


  getUsers() {
    this.isLoadingResult = true;
    this.userService.getUsers().subscribe(
      async (response: User[]) => {
          this.data = await response;
          this.isLoadingResult = false;
        },
        (error: HttpErrorResponse) => {
          this.errorMsg = error.message;
        }
    );
  }

  searchCleared() {
    console.log('searchCleared');
    this.data = [];
  }

}
