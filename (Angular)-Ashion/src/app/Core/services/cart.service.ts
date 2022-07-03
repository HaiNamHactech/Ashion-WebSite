import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ColorSizePrd } from '../models/colorSizePrd';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  public cart: Array<any> = [];
  public cartItemLocal = localStorage.getItem('carts')
    ? JSON.parse(localStorage.getItem('carts') || '{}')
    : [];
  public updateCart = new BehaviorSubject(this.cartItemLocal);

  constructor() {}

  addPrdToCart(prd: Product, quanlity: any, colorId: any, sizeId: any) {
    let prdPriceOld = prd.price;
    prd.price = Math.ceil((prdPriceOld * (100 - prd.discount)) / 100);
    var index = this.cartItemLocal.findIndex(
      (item: any) =>
        item.productId === prd.productId &&
        item.color.colorId === colorId &&
        item.size.sizeId === sizeId
    );

    let colorSizePrd: ColorSizePrd[] = prd.listColorSizeDto?.filter((item) => {
      return item.sizeId === sizeId && item.colorId === colorId;
    });

    if (index === -1 && colorSizePrd[0].quantity > 0) {
      let product = {
        productId: prd.productId,
        productName: prd.productName,
        price: prd.price,
        discount: prd.discount,
        thumbnail: prd.thumbnail,
      };
      let color = {
        colorName: colorSizePrd[0].colorName,
        colorId: colorSizePrd[0].colorId,
      };
      let size = {
        sizeName: colorSizePrd[0].sizeName,
        sizeId: colorSizePrd[0].sizeId,
      };
      this.cartItemLocal.push({
        ...product,
        quanlity: quanlity,
        color: color,
        size: size,
      });
    } else if (this.cartItemLocal[index].quanlity < colorSizePrd[0].quantity) {
      this.cartItemLocal[index].quanlity += parseInt(quanlity) * 1;
    } else {
      console.log('kho het hang');
    }
    prd.price = prdPriceOld;
    localStorage.setItem('carts', JSON.stringify(this.cartItemLocal));
    this.updateCart.next(this.cartItemLocal);
  }

  deletePrdOnCart(id: any, colorId: any, sizeId: any) {
    let index = this.cartItemLocal.findIndex(
      (prd: any) =>
        prd.color.colorId === colorId &&
        prd.size.sizeId === sizeId &&
        prd.productId === id
    );
    this.cartItemLocal.splice(index, 1);
    localStorage.setItem('carts', JSON.stringify(this.cartItemLocal));
    this.updateCart.next(this.cartItemLocal);
  }

  clearCart(): void {
    this.cartItemLocal = [];
    localStorage.setItem('carts', JSON.stringify(this.cartItemLocal));
    this.updateCart.next(this.cartItemLocal);
  }

  subtotalPricePrdBill(): number {
    let totalPrdBill = 0;
    this.cartItemLocal.map(
      (item: any) => (totalPrdBill += item.quanlity * item.price)
    );
    return totalPrdBill;
  }
}
