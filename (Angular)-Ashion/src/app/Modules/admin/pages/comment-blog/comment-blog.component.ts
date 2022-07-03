import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommentBlogService } from 'src/app/Core/services/comment-blog.service';

@Component({
  selector: 'app-comment-blog',
  templateUrl: './comment-blog.component.html',
  styleUrls: ['./comment-blog.component.css'],
})
export class CommentBlogComponent implements OnInit {
  public titleTable: string = 'Comment Blog';
  public listCommentBlog!: Comment[];
  public listCommentBlogPangination!: Comment[];
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
    private commentBlogService: CommentBlogService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllCommentBlog();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getCommentPagination();
  }

  getAllCommentBlog() {
    this.commentBlogService.getAll().subscribe((data) => {
      this.listCommentBlog = data;
      this.listKey = Object.keys(this.listCommentBlog[0]);
      this.keyId = this.listKey[0];
    });
  }

  getCommentPagination(): void {
    this.commentBlogService
      .getCommentPagination(this.filter)
      .subscribe((data) => {
        this.listCommentBlogPangination = data.content;
        this.offset = data.pageable.pageNumber + 1;
        this.pageSize = data.pageable.pageSize;
        this.lastPage = data.last;
        this.firstPage = data.first;
        this.totalPage = data.totalPages;
      });
  }

  handleDeleteItem(commentBlogId: number) {
    this.commentBlogService.delete(commentBlogId).subscribe((respon) => {
      this.toastr.success('Xóa TC Bình Luận :' + respon.commentId);
      setTimeout(() => {
        if (this.listCommentBlogPangination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getCommentPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/commentBlog/' + 'edit/' + item.commentId
    );
    this.commentBlogService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/commentBlog/' + 'detail/' + item.commentId
    );
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl(
      '/admin' + '/commentBlog' + '/page/' + pageNumber
    );
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getCommentPagination();
  }
}
