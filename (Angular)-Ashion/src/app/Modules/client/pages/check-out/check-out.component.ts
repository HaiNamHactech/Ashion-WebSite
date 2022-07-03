import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { token } from 'src/app/Core/common/token';
import { Instagram } from 'src/app/Core/models/instagram';
import { Order } from 'src/app/Core/models/order';
import { OrderDetail } from 'src/app/Core/models/order-detail';
import { AuthServiceService } from 'src/app/Core/services/auth-service.service';
import { CartService } from 'src/app/Core/services/cart.service';
import { InstagramService } from 'src/app/Core/services/instagram.service';
import { OrderDetailService } from 'src/app/Core/services/order-detail.service';
import { OrderService } from 'src/app/Core/services/order.service';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.css'],
})
export class CheckOutComponent implements OnInit {
  public listInstagram: Instagram[] = [];
  public cart: Array<any> = [];
  public totalPricePrdBill!: number;
  public formCheckOut!: FormGroup;
  public checkoutSuccess: string = '';
  public listOrderDetail: OrderDetail[] = [];
  public userId!: number;

  constructor(
    private cartService: CartService,
    private instagramService: InstagramService,
    private authService: AuthServiceService,
    private jwtHelper: JwtHelperService,
    private toastr: ToastrService,
    private fb: FormBuilder,
    private orderService: OrderService,
    private orderDetailService: OrderDetailService
  ) {}

  ngOnInit(): void {
    this.getInstagram();
    this.getAllCart();
    this.formCheckOut = this.fb.group({
      firstName: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]{6,32}$/i)],
      ],
      lastName: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]{6,32}$/i)],
      ],
      phone: [
        '',
        [
          Validators.required,
          Validators.pattern(/((09|03|07|08|05)+([0-9]{8})\b)/g),
        ],
      ],
      address: ['', [Validators.required]],
      note: [''],
      email: [
        '',
        [
          Validators.pattern(/^[\w\.]+@([\w-]+\.)+[\w-]{2,4}/),
          Validators.required,
        ],
      ],
      userId: [],
    });
    console.log(this.cart);
  }

  getAllCart() {
    this.cartService.updateCart.subscribe((data) => {
      this.cart = data;
      this.totalPricePrdBill = this.cartService.subtotalPricePrdBill();
    });
  }

  getInstagram() {
    this.instagramService.getAll().subscribe((data) => {
      this.listInstagram = data;
    });
  }

  onSubmitReceiver(form: FormGroup) {
    let codeToken = new token().codeToken;
    if (
      codeToken !== '' &&
      this.jwtHelper.isTokenExpired(codeToken) === false
    ) {
      let usetId = this.jwtHelper.decodeToken(codeToken).sub;
      form.patchValue({ userId: usetId });
      this.orderService.pushOrder(form.value).subscribe(
        (res: Order) => {
          this.toastr.success('THANH TOÁN THÀNH CÔNG MADH : ' + res.orderId);
          console.log(res);
          this.covertListCartToOrderDetail(res.orderId);
          console.log(this.listOrderDetail);
          this.orderDetailService
            .pushOrderDetail(this.listOrderDetail)
            .subscribe(
              (respon) => {
                setTimeout(() => {
                  this.cartService.clearCart();
                  this.checkoutSuccess =
                    '( Cảm ơn bạn đã mua hàng ! )MAĐH : ' + res.orderId;
                  this.formCheckOut.reset();
                }, 1000);
              },
              (err) => {
                console.log(err);
              }
            );
        },
        (err) => {
          this.checkoutSuccess = '( Thanh Toán Lỗi ! )';
        }
      );
    } else {
      this.toastr.error('Bạn phải đăng nhập trước khi thanh toán !');
      // this.userNameInput?.nativeElement.focus();
    }
  }

  covertListCartToOrderDetail(orderId: number) {
    this.cart.forEach((item: any) => {
      let orderDetail = {
        productId: item.productId,
        productName: item.productName,
        colorId: item.color.colorId,
        colorName: item.color.colorName,
        sizeId: item.size.sizeId,
        sizeName: item.size.sizeName,
        price: Math.ceil((item.price * (100 - item.discount)) / 100),
        quantity: item.quanlity,
        total:
          Math.ceil((item.price * (100 - item.discount)) / 100) * item.quanlity,
        orderId: orderId,
      };
      this.listOrderDetail.push(<OrderDetail>orderDetail);
    });
  }
}
