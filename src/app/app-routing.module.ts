import { SingleEmployeeComponent } from './employee/single-employee/single-employee.component';
import { EmployeeComponent } from './employee/employee.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormMailComponent } from './employee/form-mail/form-mail.component';
import { MailComponent } from './mail/mail.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  //login
  {path: "login", pathMatch: "full", component: LoginComponent},

  //employees
  {path: "employees", pathMatch: "full", component: EmployeeComponent},
  {path: "employees/view/:id", pathMatch: "full", component: SingleEmployeeComponent},
  {path: "employees/mail/:id", pathMatch: "full", component: FormMailComponent},

  //mails
  {path: "mails", pathMatch: "full", component: MailComponent},

  //users
  {path: "users", pathMatch: "full", component: UserComponent},

  {path: "", redirectTo: "login", pathMatch: "full"},
  {path: "**", redirectTo: "login"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
