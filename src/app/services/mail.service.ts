import { Mail } from './../models/mail.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import firebase from 'firebase';


@Injectable({
  providedIn: 'root'
})
export class MailService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }


  public getMails(): Observable<Mail[]>
  {
    return this.http.get<Mail[]>(`${this.apiServerUrl}/mails`);
  }

  public sendMail(mail: Mail, sendType: String): Observable<Mail>
  {
    return this.http.post<Mail>(`${this.apiServerUrl}/mail/${sendType}`, mail);
  }

  public deleteEmployee(mailId: number | undefined): Observable<void>
  {
    return this.http.delete<void>(`${this.apiServerUrl}/mail/delete/${mailId}`);
  }

  public uploadFile(file: File){
    return new Promise<any>(
      (resolve, rejects) => {
        const almostUniqueFileName = Date.now().toString();
        const upload = firebase.storage().ref()
                      .child('Attachements/' + almostUniqueFileName + file.name)
                      .put(file);

        upload.on(firebase.storage.TaskEvent.STATE_CHANGED,
            () =>{
              console.log("Loading...");
            },

            (error) => {
              console.log("Loading error : " + error);
              rejects();
            },

            () => {
              resolve(upload.snapshot.ref.getDownloadURL().toString());
            }
        );
      }
    );
  }


}
