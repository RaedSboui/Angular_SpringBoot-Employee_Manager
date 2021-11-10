import { HttpErrorResponse } from '@angular/common/http';
import { MailService } from './../services/mail.service';
import { Component, OnInit } from '@angular/core';
import { Mail } from '../models/mail.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.css']
})
export class MailComponent implements OnInit {
  mails!: Mail[];
  mailForm !: FormGroup;
  fileIsUploading = false;
  fileUrl !: string;
  fileUploaded = false;

  constructor(private mailService: MailService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {
  }

  initForm(){
    this.mailForm = this.formBuilder.group({
      title: ['', Validators.required],
      author: ['', Validators.required]
    });
  }

  getEmployees(): void {
    this.mailService.getMails().subscribe(
      (response: Mail[]) => {
        this.mails = response;
        console.log(this.mails);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  onUploadFile(file: File) {
    this.fileIsUploading = true;
    this.mailService.uploadFile(file).then(
      (url: string) => {
        this.fileUrl = url;
        this.fileIsUploading = false;
        this.fileUploaded = true;
      }
    )
  }

  detectFile(event: any) {
    this.onUploadFile(event.target.files[0]);
  }

}
