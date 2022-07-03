import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  encapsulation: ViewEncapsulation.Emulated,
})
export class AdminComponent implements OnInit {
  public checkCustomStyle!: boolean;
  constructor() {}

  ngOnInit(): void {}

  public handleCustomStyle(show: boolean) {
    this.checkCustomStyle = show;
  }
}
