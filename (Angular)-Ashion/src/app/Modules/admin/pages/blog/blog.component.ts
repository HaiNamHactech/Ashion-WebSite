import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Blog } from 'src/app/Core/models/blog';
import { CategoryBlog } from 'src/app/Core/models/categoryBlog';
import { BlogService } from 'src/app/Core/services/blog.service';
import { CategoryBlogService } from 'src/app/Core/services/category-blog.service';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css'],
})
export class BlogComponent implements OnInit {
  public titleTable: string = 'Blog';
  public listBlog!: Blog[];
  public listBlogPagination!: Blog[];
  public listKey: Array<any> = [];
  public keyId: any;
  public showAction: boolean = true;
  public checkLoadding: boolean = false;
  public listCateBlog!: CategoryBlog[];

  public curentPage!: any;
  public offset!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filedTable: object = {
    blogId: 'Id',
    categoryName: 'Category',
    titleBlog: 'Title',
    urlImg: 'Thumnail',
    totalComment: 'Total comment',
    createBy: 'CreateBy',
    modifieBy: 'ModifieBy',
    createDate: 'CreateDate',
    modifieDate: 'ModifieDate',
  };

  public filter: any = {
    page: 0,
    pageSize: 3,
    asc: true,
  };

  constructor(
    private toastr: ToastrService,
    private _router: Router,
    private blogService: BlogService,
    private categoryBlogService: CategoryBlogService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllBlog();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getBlogPagination();
  }

  getAllBlog(): void {
    this.blogService.getAll().subscribe((data) => {
      this.listBlog = data;
      this.listKey = Object.keys(this.filedTable);
      this.keyId = this.listKey[0];
    });
  }

  getBlogPagination(): void {
    console.log(this.filter);
    this.blogService.getBlogPagination(this.filter).subscribe((data) => {
      this.listBlogPagination = data.content;
      console.log(this.listBlogPagination);

      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(blogId: any) {
    console.log(blogId);
    this.blogService.deleteBlog(blogId).subscribe((res) => {
      this.toastr.success('Xóa TC bài viết : ' + res.titleBlog);
      setTimeout(() => {
        if (this.listBlogPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getBlogPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/blog/' + 'edit/' + item.blogId);
    this.blogService.notifyPageNumberValue(
      this.offset - 1 + ',' + this.pageSize
    );
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl('/admin' + '/blog/' + 'detail/' + item.blogId);
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-blog');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/blog/' + 'page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getBlogPagination();
  }
}
