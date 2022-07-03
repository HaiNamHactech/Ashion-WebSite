import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CategoryBlog } from 'src/app/Core/models/categoryBlog';
import { CategoryBlogService } from 'src/app/Core/services/category-blog.service';

@Component({
  selector: 'app-category-blog',
  templateUrl: './category-blog.component.html',
  styleUrls: ['./category-blog.component.css'],
})
export class CategoryBlogComponent implements OnInit {
  public titleTable: string = 'Category Blog';
  public listCateBlog!: CategoryBlog[];
  public listCategoryPagination!: CategoryBlog[];
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
  public filter: any = {
    page: 0,
    pageSize: 3,
    asc: true,
  };

  constructor(
    private categoryBlogService: CategoryBlogService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllCategoryBlog();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getCategoryPagination();
  }

  getAllCategoryBlog() {
    this.categoryBlogService.getAllCategoryBlog().subscribe((data) => {
      this.listCateBlog = data;
      this.listKey = Object.keys(this.listCateBlog[0]);
      this.keyId = this.listKey[0];
    });
  }

  getCategoryPagination(): void {
    console.log(this.filter);
    this.categoryBlogService
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

  handleDeleteItem(cateId: any) {
    this.categoryBlogService.deleteCategoryBlog(cateId).subscribe((respon) => {
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
      '/admin' + '/category-blog/' + 'edit/' + item.categoryId
    );
    this.categoryBlogService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/category-blog/' + 'detail/' + item.categoryId
    );
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-category-blog');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl(
      '/admin' + '/categoryBlog' + '/page/' + pageNumber
    );
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getCategoryPagination();
  }
}
