import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/Core/models/product';

@Component({
  selector: 'app-trend-item',
  templateUrl: './trend-item.component.html',
  styleUrls: ['./trend-item.component.css']
})
export class TrendItemComponent implements OnInit {

  @Input() urlImg !: string;
  @Input() namePrd !: string;
  @Input() vote !: number;
  @Input() price !: number;

  @Input() product: Product = new Product();
  listVote: Array<string> = [];

  constructor(private route : Router) { }

  ngOnInit(): void {
    if(this.vote > 0) {
      for(let i = 0 ; i < this.vote; i++){
        this.listVote?.push('');
      }
    }
  }

  navigateToDetail(prd: Product) {

      this.route.navigateByUrl(
        '/products/' + prd.productId + '-' + prd.productName
      );
  }

}
