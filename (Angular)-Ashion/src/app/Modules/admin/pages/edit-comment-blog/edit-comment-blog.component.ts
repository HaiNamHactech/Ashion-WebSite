import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommentBlogService } from 'src/app/Core/services/comment-blog.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-edit-comment-blog',
  templateUrl: './edit-comment-blog.component.html',
  styleUrls: ['./edit-comment-blog.component.css'],
})
export class EditCommentBlogComponent implements OnInit {
  formCommentBlog!: FormGroup;

  public formEditPrdSizeColor!: FormGroup;
  public checkAction!: any;
  private commentBlogId!: any;
  private action!: any;
  private commentBlog!: Comment;
  public urlAvatar: any = '../../../../../assets/imagesAdmin/avatar.jpg';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private tableService: TableService,
    private _router: Router,
    private commentBlogService: CommentBlogService
  ) {}

  ngOnInit(): void {
    this.formCommentBlog = this.fb.group({
      userId: [''],
      blogId: [''],
      createBy: [''],
      modifieBy: [''],
      content: [''],
      createDate: [''],
      modifieDate: [''],
      favourite: [''],
      avatar: [''],
      status: [''],
    });

    this.commentBlogId = this.route.snapshot.paramMap?.get('commentId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.commentBlogId != null) {
      if (this.action === 'edit') {
        this.loadDataToForm();
        this.checkAction = 1;
      }
      if (this.action === 'detail') {
        this.loadDataToForm();
        this.checkAction = 2;
      }
    }
  }

  loadDataToForm(): void {
    this.commentBlogService.getById(this.commentBlogId).subscribe((res) => {
      this.formCommentBlog.patchValue({
        userId: res.userId,
        blogId: res.blogId,
        createBy: res.createBy,
        modifieBy: res.modifieBy,
        content: res.content,
        createDate: res.createDate,
        modifieDate: res.modifieDate,
        favourite: res.favourite,
        avatar: res.avatar,
        status: res.status,
      });
      this.urlAvatar = res.avatar;
    });
  }

  updateProduct(commentBlog: Comment) {
    let pageNumber!: number;
    this.commentBlogService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.commentBlogService.update(this.commentBlogId, commentBlog).subscribe(
      (res) => {
        this.toastr.success('sửa thành công : ');
        this.tableService.notifyCountValue(res.commentBlogId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/commentBlog/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi');
        }, 1000);
      }
    );
  }

  submitForm(form: any) {
    this.commentBlog = <Comment>form.value;
    console.log(this.commentBlog);
    this.updateProduct(this.commentBlog);
  }
}
