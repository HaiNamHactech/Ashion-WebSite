import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Blog } from 'src/app/Core/models/blog';
import { CategoryBlog } from 'src/app/Core/models/categoryBlog';
import { BlogService } from 'src/app/Core/services/blog.service';
import { CategoryBlogService } from 'src/app/Core/services/category-blog.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-blog',
  templateUrl: './add-blog.component.html',
  styleUrls: ['./add-blog.component.css'],
})
export class AddBlogComponent implements OnInit {
  public formAddBlog!: FormGroup;
  public checkLoadding: boolean = false;
  public blogId!: any;
  public action!: any;
  public checkAction: any = 0;
  public imageFile: any = '../../../../../assets/img/up.png';
  public listCategoryBlog!: CategoryBlog[];
  public blog!: Blog;

  constructor(
    private fb: FormBuilder,
    private blogService: BlogService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private tableService: TableService,
    private _router: Router,
    private cateBlogService: CategoryBlogService
  ) {}

  ngOnInit(): void {
    this.getAllCateBlog();
    this.formAddBlog = this.fb.group({
      categoryId: ['', [Validators.required]],
      titleBlog: ['', [Validators.required]],
      content: ['', [Validators.required]],
      urlImg: ['', [Validators.required]],
    });

    this.blogId = this.route.snapshot.paramMap?.get('blogId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.blogId != null) {
      if (this.action === 'edit') {
        this.loadDataToUpdate();
        this.checkAction = 1;
      }
      if (this.action === 'detail') {
        this.loadDataToUpdate();
        this.checkAction = 2;
      }
    }
  }

  getAllCateBlog(): void {
    this.cateBlogService.getAllCategoryBlog().subscribe((data) => {
      this.listCategoryBlog = data;
    });
  }

  loadDataToUpdate(): void {
    this.blogService.getById(this.blogId).subscribe(
      (respon) => {
        this.formAddBlog.patchValue({
          categoryId: respon.categoryId,
          titleBlog: respon.titleBlog,
          content: respon.content,
          urlImg: respon.urlImg,
        });
        this.imageFile = respon.urlImg;
      },
      (error) => {
        this.toastr.error('không tìm thấy bài viết : ' + this.blogId);
      }
    );
  }

  addBlog(blog: Blog) {
    console.log(blog);
    this.blogService.add(blog).subscribe(
      (res) => {
        this.toastr.success('thêm thành công danh mục  : ' + res.titleBlog);
        this.tableService.notifyCountValue(res.blogId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/blog');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi danh mục  : ' + err.titleBlog);
        }, 1000);
      }
    );
  }

  updateBlog(blog: Blog) {
    let pageNumber!: number;
    this.blogService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.blogService.update(this.blogId, blog).subscribe(
      (res) => {
        this.toastr.success('sửa thành công bài viết  : ' + res.titleBlog);
        this.tableService.notifyCountValue(res.blogId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/blog/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi bài viết : ' + err.titleBlog);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    this.blog = <Blog>form.value;
    console.log(this.blog);

    if (this.checkAction === 0) {
      this.addBlog(this.blog);
    }
    if (this.checkAction === 1) {
      this.updateBlog(this.blog);
    }
  }

  onchangeImage(event: any) {
    this.checkLoadding = true;
    console.log(event.target.files[0]);
    this.blogService.upload(event.target.files[0]).subscribe(
      (res) => {
        this.imageFile = res.secure_url;
        console.log(this.imageFile);
        this.formAddBlog.patchValue({
          urlImg: this.imageFile,
        });
        this.checkLoadding = false;
      },
      (error) => {
        this.imageFile = '../../../../../assets/img/uploadError.jpg';
      }
    );
  }
}
