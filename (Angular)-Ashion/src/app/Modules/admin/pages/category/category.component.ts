import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CATEGORY_PRODUCT_API } from 'src/app/Core/common/baseAPI';
import { CategoryPrd } from 'src/app/Core/models/categoryPrd';
import { CategoryProductService } from 'src/app/Core/services/category-product.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  public titleTable: string = 'Category Product';
  public listCategoryProduct: Array<any> = [];
  public listCategoryPagination!: CategoryPrd[];
  public listKey: Array<any> = [];
  public keyId: any;
  public showAction: boolean = true;
  public checkLoadding: boolean = false;

  public curentPage!: any;
  public offset!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filedTable: object = {
    categoryId: 'Id',
    categoryName: 'Name',
    parentId: 'parentId',
    showHome: 'showHome',
    urlImg: 'Thumbnail',
    totalItemPrd: 'totalItemPrd',
  };

  public filter: any = {
    page: 0,
    pageSize: 3,
    asc: true,
  };

  constructor(
    private categoryPrdService: CategoryProductService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllCategoryPrd();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getCategoryPagination();
  }

  getAllCategoryPrd() {
    this.categoryPrdService.getAllCategoryPrd().subscribe((data) => {
      this.listCategoryProduct = data;
      this.listKey = Object.keys(this.filedTable);
      this.keyId = this.listKey[0];
    });
  }

  getCategoryPagination(): void {
    console.log(this.filter);
    this.categoryPrdService
      .getCategoryPagination(this.filter)
      .subscribe((data) => {
        this.listCategoryPagination = data.content;
        this.offset = data.pageable.pageNumber + 1;
        this.pageSize = data.pageable.pageSize;
        this.lastPage = data.last;
        this.firstPage = data.first;
        this.totalPage = data.totalPages;
      });
  }

  handleDeleteItem(categoryId: any) {
    this.categoryPrdService
      .deleteCategoryPrd(categoryId)
      .subscribe((respon) => {
        this.toastr.success('Xóa TC Danh Mục :' + respon.categoryName);
        setTimeout(() => {
          if (this.listCategoryPagination.length <= 1) {
            this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
          } else {
            this.getCategoryPagination();
          }
        }, 1000);
      });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/category-prd/' + 'edit/' + item.categoryId
    );
    this.categoryPrdService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/category-prd/' + 'detail/' + item.categoryId
    );
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-category-prd');
  }

  handleSelectPage(page: any) {
    console.log(page);

    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl(
      '/admin' + '/categoryProduct' + '/page/' + pageNumber
    );
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getCategoryPagination();
  }
}
