       //firebase import modules
import { AngularFireStorageModule } from '@angular/fire/storage';
import { AngularFireModule } from '@angular/fire';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { EmployeeService } from './services/employee.service';
import { MailService } from './services/mail.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MailComponent } from './mail/mail.component';
import { EmployeeComponent } from './employee/employee.component';
import { SingleEmployeeComponent } from './employee/single-employee/single-employee.component';
import { FormMailComponent } from './employee/form-mail/form-mail.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { UserService } from './services/user.service';
import { UserComponent } from './user/user.component';
import { AutocompleteLibModule } from 'angular-ng-autocomplete';



@NgModule({
  declarations: [
    AppComponent,
    MailComponent,
    EmployeeComponent,
    SingleEmployeeComponent,
    FormMailComponent,
    HeaderComponent,
    LoginComponent,
    UserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AutocompleteLibModule,

    //firebase modules
    AngularFireModule,
    AngularFireStorageModule,
  ],
  providers: [
    EmployeeService,
    MailService,
    UserService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
