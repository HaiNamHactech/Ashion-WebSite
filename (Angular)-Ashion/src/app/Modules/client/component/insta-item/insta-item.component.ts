import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-insta-item',
  templateUrl: './insta-item.component.html',
  styleUrls: ['./insta-item.component.css']
})
export class InstaItemComponent implements OnInit {

  @Input() urlImg !: string;

  constructor() { }

  ngOnInit(): void {
  }

}
