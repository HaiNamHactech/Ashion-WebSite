import { Component, OnInit } from '@angular/core';
import { AbstractControl, ValidationErrors } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { map, Observable } from 'rxjs';
import { Product } from 'src/app/Core/models/product';
import { ProductService } from 'src/app/Core/services/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  public titleTable: string = 'Table Product';
  public listProduct!: Product[];
  public listPrdPagination!: Product[];
  public listKey: Array<any> = [];
  public keyId: any;
  public showAction: boolean = true;
  public checkAction: any = 0;
  public checkLoadding: boolean = false;

  public curentPage!: any;
  public offset!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filedTable: object = {
    productId: 'Id',
    categoryName: 'CategoryName',
    productName: 'Name',
    newProduct: 'New',
    trendProduct: 'Trend',
    featureProduct: 'Feature',
    price: 'Price',
    discount: 'Discount',
    listColorSizeDto: 'Stock',
    thumbnail: 'Thumbnail',
    urlImgs: 'SubImages',
    status: 'Status',
    createDate: 'CreateDate',
    modifieDate: 'ModifieDate',
    createBy: 'CreateBy',
    modifieBy: 'ModifieBy',
  };

  public filter: any = {
    page: 0,
    pageSize: 3,
    asc: true,
  };
  constructor(
    private toastr: ToastrService,
    private _router: Router,
    private productService: ProductService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllProduct();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getPrdPagination();
  }

  getAllProduct(): void {
    this.productService.getAllProduct().subscribe((data) => {
      this.listProduct = data;
      this.listKey = Object.keys(this.filedTable);
      this.keyId = this.listKey[0];
    });
  }

  getPrdPagination(): void {
    this.productService.getProductPagination(this.filter).subscribe((data) => {
      this.listPrdPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(productId: any) {
    this.productService.deleteProduct(productId).subscribe((respon) => {
      this.toastr.success('Xóa TC Sản Phẩm :' + respon.productName);
      setTimeout(() => {
        if (this.listPrdPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getPrdPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/product/' + 'edit/' + item.productId
    );
    this.productService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/product/' + 'detail/' + item.productId
    );
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-product');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/product' + '/page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getPrdPagination();
  }
}
