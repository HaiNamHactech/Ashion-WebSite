import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EMPTY, map, Observable, of, catchError } from 'rxjs';
import { CategoryBlog } from 'src/app/Core/models/categoryBlog';
import { CategoryBlogService } from 'src/app/Core/services/category-blog.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-category-blog',
  templateUrl: './add-category-blog.component.html',
  styleUrls: ['./add-category-blog.component.css'],
})
export class AddCategoryBlogComponent implements OnInit {
  public formAddCategory!: FormGroup;
  public cateId!: any;
  public action!: any;
  public checkAction: any = 0;
  public checkLoadding: boolean = false;
  public cateBlog!: CategoryBlog;
  public listCateBlog!: CategoryBlog[];

  constructor(
    private fb: FormBuilder,
    private categoryBlogService: CategoryBlogService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private tableService: TableService,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this.getAllCategoryBlog();
    this.formAddCategory = this.fb.group({
      categoryName: [
        '',
        [Validators.required, Validators.minLength(4)],
        this.validateFromAPI.bind(this),
      ],
      slug: [
        '',
        [Validators.required, Validators.pattern(/^[a-zA-Z]{6,23}$/)],
        this.validateFromAPI.bind(this),
      ],
      parentId: ['', [Validators.required]],
    });

    this.cateId = this.route.snapshot.paramMap?.get('cateId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.cateId != null) {
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

  getAllCategoryBlog(): void {
    this.categoryBlogService.getAllCategoryBlog().subscribe((data) => {
      this.listCateBlog = data;
    });
  }

  loadDataToUpdate(): void {
    this.categoryBlogService.getCategoryBlogById(this.cateId).subscribe(
      (respon) => {
        this.formAddCategory.patchValue({
          categoryName: respon.categoryName,
          slug: respon.slug,
          parentId: respon.parentId,
        });
      },
      (error) => {
        this.toastr.error('không tìm thấy danh mục : ' + this.cateId);
      }
    );
  }

  addCategory(categoryBlog: CategoryBlog) {
    this.categoryBlogService.postCategoryBlog(categoryBlog).subscribe(
      (res) => {
        this.toastr.success('thêm thành công danh mục  : ' + res.categoryName);
        this.tableService.notifyCountValue(res.categoryId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/categoryBlog');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi danh mục  : ' + err.categoryName);
        }, 1000);
      }
    );
  }

  updateCategory(categoryBlog: CategoryBlog) {
    let pageNumber!: number;
    this.categoryBlogService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.categoryBlogService
      .updateCategoryBlog(this.cateId, categoryBlog)
      .subscribe(
        (res) => {
          this.toastr.success('sửa thành công danh mục  : ');
          this.tableService.notifyCountValue(res.categoryId);
          setTimeout(() => {
            this._router.navigateByUrl(
              '/admin/categoryBlog/page/' + pageNumber
            );
          }, 1000);
        },
        (err) => {
          setTimeout(() => {
            this.toastr.error('sửa lỗi danh mục : ' + err.categoryName);
          }, 1000);
        }
      );
  }

  submitForm(form: FormGroup) {
    let check = true;
    for (let key in form.controls) {
      if (form.controls[key].status === 'INVALID') {
        check = false;
      }
    }
    if (check) {
      this.cateBlog = <CategoryBlog>form.value;
      if (this.checkAction === 0) {
        this.addCategory(this.cateBlog);
      }
      if (this.checkAction === 1) {
        this.updateCategory(this.cateBlog);
      }
    }
  }

  getNameFormControlByValue(control: AbstractControl): any | null {
    let group = <FormGroup>control.parent;
    if (!group) {
      return null;
    }
    let name: any;
    Object.keys(group.controls).forEach((key) => {
      let childControl = group.get(key);

      if (childControl !== control) {
        return;
      }

      name = key;
    });

    return name;
  }

  validateFromAPI(
    control: AbstractControl
  ): Observable<ValidationErrors | null> {
    let filed = this.getNameFormControlByValue(control);
    if (this.checkAction === 2) {
      return of(null);
    }
    return this.categoryBlogService
      .getCategoryByFiled(control.value, filed)
      .pipe(
        map((isValid) => {
          if (isValid.length === 0) {
            return null;
          }
          if (isValid.length !== 0 && this.cateId == isValid.categoryId) {
            return null;
          }
          return {
            FiledDuplicated: true,
          };
        }),
        catchError(() => {
          return EMPTY;
        })
      );
  }
}
