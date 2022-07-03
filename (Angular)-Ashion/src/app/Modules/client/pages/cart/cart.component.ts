import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Instagram } from 'src/app/Core/models/instagram';
import { CartService } from 'src/app/Core/services/cart.service';
import { InstagramService } from 'src/app/Core/services/instagram.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  public listInstagram : Instagram[] = [];
  listVote: Array<string> = [];
  public listCart: Array<any> = [];
  public totalPricePrdBill!: number;
  constructor(
    private cartService: CartService,
    private location: Location,
    private toastr: ToastrService,
    private instagramService : InstagramService,
    private route : Router
  ) {}

  ngOnInit(): void {
    this.getInstagram();
    this.getAllCart();
  }

  getAllCart() {
    this.cartService.updateCart.subscribe((data) => {
      this.listCart = data;
      this.totalPricePrdBill = this.cartService.subtotalPricePrdBill();
    });
  }

  deletePrd(idPrd: any) {
    this.toastr.success(`Xóa sản phẩm thành công !`);
    setTimeout(() => {
      // this.cartService.deletePrdOnCart(idPrd);
    }, 1000);
  }

  goBackUrl() {
    this.location.back();
  }

  clearCart() {
    this.toastr.success(`Xóa toàn bộ giỏ hàng !`);
    setTimeout(() => {
      this.cartService.clearCart();
    }, 1000);
  }

  updateQuanlity() {
    this.cartService.updateCart.next(this.listCart);
    localStorage.setItem('carts', JSON.stringify(this.listCart));
  }

  getInstagram() {
    this.instagramService.getAll().subscribe(data => {
      this.listInstagram = data;
    })
  }

  deletePrdOnCart(productId: any, colorId: any, sizeId: any) {
    this.toastr.success(`Xóa sản phẩm thành công !`);
    setTimeout(() => {
      this.cartService.deletePrdOnCart(productId, colorId, sizeId);
    }, 1000);
  }

  // chuyển hướng đến trang chi tiết sản phẩm //
  navigateToDetail(prd: any) {
    this.route.navigateByUrl(
      '/products/' + prd.productId + '-' + prd.productName
    );
  }
}
