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
import { catchError, EMPTY, map, Observable, of } from 'rxjs';
import { CategoryPrd } from 'src/app/Core/models/categoryPrd';
import { CategoryProductService } from 'src/app/Core/services/category-product.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-category-prd',
  templateUrl: './add-category-prd.component.html',
  styleUrls: ['./add-category-prd.component.css'],
})
export class AddCategoryPrdComponent implements OnInit {
  public formAddCategory!: FormGroup;
  public cateId!: any;
  public action!: any;
  public checkAction: any = 0;
  public imageFile: any = '../../../../../assets/img/up.png';
  public checkLoadding: boolean = false;
  public listCategory!: CategoryPrd[];
  public categoryPrd!: CategoryPrd;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryProductService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private _router: Router,
    private tableService: TableService
  ) {}

  ngOnInit(): void {
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
      displayNo: ['', [Validators.required], this.validateFromAPI.bind(this)],
      description: [''],
      showHome: ['', Validators.required],
      urlImg: ['', [Validators.required]],
      breadcrumbLink: ['', [Validators.required]],
      parentId: ['', [Validators.required]],
    });

    this.cateId = this.route.snapshot.paramMap?.get('category');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.cateId != null) {
      if (this.action === 'edit') {
        this.loadDataToUpdate();
        this.checkAction = 1;
        this.validateFromAPI.bind(this);
      }
      if (this.action === 'detail') {
        this.loadDataToUpdate();
        this.checkAction = 2;
      }
    }

    this.getAllCategoryPrd();
  }

  getAllCategoryPrd() {
    this.categoryService.getAllCategoryPrd().subscribe((res) => {
      this.listCategory = res;
    });
  }

  loadDataToUpdate(): void {
    this.categoryService.getCategoryPrdById(this.cateId).subscribe(
      (respon) => {
        this.formAddCategory.patchValue({
          categoryName: respon.categoryName,
          parentId: respon.parentId,
          slug: respon.slug,
          displayNo: respon.displayNo,
          description: respon.description,
          showHome: respon.showHome,
          breadcrumbLink: respon.breadcrumbLink,
          urlImg: respon.urlImg,
        });
        this.imageFile = respon.urlImg;
      },
      (error) => {
        this.toastr.error('không tìm thấy danh mục : ' + this.cateId);
      }
    );
  }

  addCategoryPrd(categoryPrd: CategoryPrd) {
    this.categoryService.postCategoryPrd(categoryPrd).subscribe(
      (res) => {
        this.toastr.success('thêm thành công danh mục : ' + res.categoryName);
        this.tableService.notifyCountValue(res.categoryId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/categoryProduct');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi danh mục : ' + err.categoryName);
        }, 1000);
      }
    );
  }

  updateCategory(categoryPrd: CategoryPrd) {
    let pageNumber!: number;
    this.categoryService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.categoryService.updateCategoryPrd(this.cateId, categoryPrd).subscribe(
      (res) => {
        this.toastr.success('sửa thành công danh mục : ');
        this.tableService.notifyCountValue(res.categoryId);
        setTimeout(() => {
          this._router.navigateByUrl(
            '/admin/categoryProduct/page/' + pageNumber
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
      this.categoryPrd = <CategoryPrd>form.value;
      if (this.checkAction === 0) {
        this.addCategoryPrd(this.categoryPrd);
      }
      if (this.checkAction === 1) {
        this.updateCategory(this.categoryPrd);
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
    return this.categoryService.getCategoryByFiled(control.value, filed).pipe(
      map((isValid) => {
        if (isValid.length === 0) {
          return null;
        }
        if (isValid.length !== 0 && this.cateId == isValid.categoryId) {
          return null;
        }
        return { FiledDuplicated: true };
      }),
      catchError(() => {
        return EMPTY;
      })
    );
  }

  onchangeImage(event: any) {
    this.checkLoadding = true;
    console.log(event.target.files[0]);
    this.categoryService.upload(event.target.files[0]).subscribe(
      (res) => {
        this.imageFile = res.secure_url;
        console.log(this.imageFile);
        this.formAddCategory.patchValue({
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
