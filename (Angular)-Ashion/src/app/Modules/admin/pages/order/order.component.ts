import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Order } from 'src/app/Core/models/order';
import { OrderService } from 'src/app/Core/services/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css'],
})
export class OrderComponent implements OnInit {
  public listOrderPagination: Order[] = [];
  public listKey!: Array<string>;
  public keyId: any;
  public showAction: boolean = true;
  public checkAction: any = 0;
  public titleTable: string = 'Order';
  public checkLoadding: boolean = false;

  public filedTable: object = {
    orderId: 'Id',
    firstName: 'FirstName',
    phone: 'phone',
    address: 'Address',
    total: 'total',
    status: 'status',
    email: 'email',
    createBy: 'CreateBy',
    modifieBy: 'ModifieBy',
    createDate: 'CreateDate',
    modifieDate: 'ModifieDate',
  };

  public curentPage!: any;
  public offset!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filter: any = {
    page: 0,
    pageSize: 3,
    asc: true,
  };

  constructor(
    private toastr: ToastrService,
    private _router: Router,
    private orderService: OrderService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.listKey = Object.keys(this.filedTable);
    this.keyId = this.listKey[0];
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getPrdPagination();
  }

  getPrdPagination(): void {
    this.orderService.getOrderPagination(this.filter).subscribe((data) => {
      this.listOrderPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(productId: any) {
    // this.productService.deleteProduct(productId).subscribe((respon) => {
    //   this.toastr.success('Xóa TC Sản Phẩm :' + respon.productName);
    //   setTimeout(() => {
    //     if (this.listPrdPagination.length <= 1) {
    //       this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
    //     } else {
    //       this.getPrdPagination();
    //     }
    //   }, 1000);
    // });
  }

  handleEditItem(item: any) {
    // this._router.navigateByUrl(
    //   '/admin' + '/product/' + 'edit/' + item.productId
    // );
    // this.productService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    // this._router.navigateByUrl(
    //   '/admin' + '/product/' + 'detail/' + item.productId
    // );
  }

  handleNavigate() {
    // this._router.navigateByUrl('/admin' + '/add-product');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/order' + '/page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getPrdPagination();
  }
}
