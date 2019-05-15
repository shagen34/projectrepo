import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  pageTitle = 'Welcome to a free music streaming service!';
  info = "Click open library to see the songs we have."
  constructor() { }

  ngOnInit() {
  }

}
