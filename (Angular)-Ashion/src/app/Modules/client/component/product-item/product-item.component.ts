import { Component, Input, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ColorSizePrd } from 'src/app/Core/models/colorSizePrd';
import { Product } from 'src/app/Core/models/product';
import { CartService } from 'src/app/Core/services/cart.service';
import { CategoryProductService } from 'src/app/Core/services/category-product.service';
import { ProductService } from 'src/app/Core/services/product.service';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css'],
})
export class ProductItemComponent implements OnInit {
  @Input() product: Product = new Product();
  @Input() slugCategory!: string;
  listVote: Array<string> = [];
  listColor: ColorSizePrd[] = [];
  colorId!: number;
  sizeId!: number;

  constructor(
    private route: Router,
    private categoryService: CategoryProductService,
    private productService: ProductService,
    private cartService: CartService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    if (this.product?.vote > 0) {
      for (let i = 0; i < this.product.vote; i++) {
        this.listVote?.push('');
      }
    }
  }

  navigateToDetail(prd: Product) {
    if (this.slugCategory !== undefined) {
      this.route.navigateByUrl(
        '/products/' +
          this.slugCategory +
          '/' +
          prd.productId +
          '-' +
          prd.productName
      );
    }
    if (this.slugCategory === '') {
      this.route.navigateByUrl(
        '/products/' + prd.productId + '-' + prd.productName
      );
    }
  }

  getListColor(product: Product) {
    this.listColor = [];
    this.listColor = product.listColorSizeDto.filter(
      (value, index, self) =>
        index === self.findIndex((c) => c.colorId === value.colorId)
    );
    this.colorId = this.listColor[0]?.colorId;
    this.sizeId = this.listColor[0]?.sizeId;
    console.log(this.listColor);
  }

  addPrdToCart(product: Product) {
    this.getListColor(product);
    this.toastr.success(`Thêm sản phẩm thành công !`);
    setTimeout(() => {
      this.cartService.addPrdToCart(product, 1, this.colorId, this.sizeId);
    }, 1000);
  }
  
}
