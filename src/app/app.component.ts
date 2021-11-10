import { environment } from 'src/environments/environment';

import { Component, OnInit } from '@angular/core';

import firebase from 'firebase';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  constructor() {
    firebase.initializeApp(environment.firebaseConfig);
  }

  ngOnInit(): void {}


}
