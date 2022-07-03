import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-category-item',
  templateUrl: './category-item.component.html',
  styleUrls: ['./category-item.component.css']
})
export class CategoryItemComponent implements OnInit {

  @Input() title !: string;
  @Input() content !: string;
  @Input() img !:string;
  @Input() totalItem !: any;
  @Input() customStyle !:any;
  @Input() customHight !: string;
  constructor() { }

  ngOnInit(): void {
  }

}
