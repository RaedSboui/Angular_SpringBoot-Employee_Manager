import { HttpErrorResponse } from '@angular/common/http';
import { EmployeeService } from './../../services/employee.service';
import { MailService } from './../../services/mail.service';
import { Component, OnInit, Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Mail } from 'src/app/models/mail.model';
import { Employee } from 'src/app/models/employee.model';


@Component({
  selector: 'app-form-mail',
  templateUrl: './form-mail.component.html',
  styleUrls: ['./form-mail.component.css']
})

@Injectable({
  providedIn: 'root'
})
export class FormMailComponent implements OnInit {

  mailForm !: FormGroup;
  fileIsUploading = false;
  fileUrl !: string;
  fileUploaded = false;
  employee !: Employee;
  sendType!: String;


  constructor( private formBuilder: FormBuilder,
               private mailService: MailService,
               private employeeService: EmployeeService,
               private router: Router,
               private route : ActivatedRoute) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm(){
    this.mailForm = this.formBuilder.group({
      subject: ['', Validators.required],
      text: ['', Validators.required]
    });
  }

  onSendMail(){
    const id = this.route.snapshot.params['id'];

    //Select employee and send email after select
    this.employeeService.getSingleEmployee(+id).subscribe(
      (response: Employee) => {
        this.employee = response;
        //console.log("Employee: ", response)


        //prepare Mail Object
        const subject = this.mailForm.get('subject')?.value;
        const text = this.mailForm.get('text')?.value;
        let newMail = new Mail( this.employee, subject, text );

        if(this.fileUrl && this.fileUrl !== '') {
          newMail.joinFile = this.fileUrl.toString();
          this.sendType = "sendwithattachment";
        }else {
          newMail.joinFile = "";
          this.sendType = "send"
        }

        //Send Mail to Employee selected
        this.mailService.sendMail(newMail, this.sendType).subscribe(
          (response: Mail) => {
            console.log(response)
          },
          (error: HttpErrorResponse) => {
            alert(error.message);
            //console.log(error)
          }
        );
        this.router.navigate(['/employees']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


  detectFile(event: any) {
    this.onUploadFile(event.target.files[0]);
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

}
