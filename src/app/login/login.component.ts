import { AuthenticationResponse } from './../models/authenticationResponse.model';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user.model';
import { UserService } from '../services/user.service';
import { Login } from '../models/login.model';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  form : any = {};
  isLoginFailed : boolean = false;
  show : string = 'fa fa-fw fa-eye field-icon';
  type : string = 'password';

  constructor( private userService : UserService,
               private router : Router) { }

  ngOnInit(): void {}

  onLoggedIn(){
    this.userService.authentication(new Login(this.form.username, this.form.password)).subscribe(
      (data)=>{
        console.log(data);
        this.isLoginFailed = false;
        this.userService.saveToken(data.accessToken);
        this.router.navigate(['/employees']);
      },
      (error)=>{
        this.isLoginFailed = true;
      }
    );
  }

  showPassword() {
    this.show = (this.show) == 'fa fa-fw fa-eye field-icon'? 'fa fa-fw fa-eye-slash field-icon' : 'fa fa-fw fa-eye field-icon';
    this.type = (this.show) == 'fa fa-fw fa-eye-slash field-icon' ? 'text' : 'password';
  }
}
