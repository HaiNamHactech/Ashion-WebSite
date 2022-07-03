import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

  public ListOrderDetail : Array<any> = [
    {
      UserNameOrder : 'nguyennamhai2k2pc@gmail.com',
      UserReceiver : 'Nam Hải',
      Address : '114 Nguyên Khiết, Phúc Tân, Hoàn Kiếm, Hà Nội',
      NameProduct : 'Quần Bò',
      Quantity : 3,
      Color : 'xanh',
      Size : 'XXL',
      Price : 1200,
      Total : 3600
    },
    {
      UserNameOrder : 'buithithuhuyen2310@gmail.com',
      UserReceiver : 'Thu Huyen',
      Address : '92 Đội Cấn,Ba Đình,Hà Nội',
      NameProduct : 'Váy Nữ',
      Quantity :1,
      Color : 'trắng',
      Size : 'XXL',
      Price : 2400,
      Total : 2400
    },
    {
      UserNameOrder : 'thanhtrungnguyen34@gmail.com',
      UserReceiver : 'Thanh Trung',
      Address : '114 Nguyên Khiết, Phúc Tân, Hoàn Kiếm, Hà Nội',
      NameProduct : 'Quần Bò',
      Quantity : 3,
      Color : 'xanh',
      Size : 'XXL',
      Price : 1200,
      Total : 3600
    },
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
