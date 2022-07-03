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
import { Color } from 'src/app/Core/models/color';
import { ColorService } from 'src/app/Core/services/color.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-color',
  templateUrl: './add-color.component.html',
  styleUrls: ['./add-color.component.css'],
})
export class AddColorComponent implements OnInit {
  public formAddColor!: FormGroup;
  public colorId!: any;
  public action!: any;
  public checkAction: any = 0;
  public checkLoadding: boolean = false;
  public color!: Color;

  constructor(
    private fb: FormBuilder,
    private colorService: ColorService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private _router: Router,
    private tableService: TableService
  ) {}

  ngOnInit(): void {
    this.formAddColor = this.fb.group({
      code: [
        '',
        [Validators.required, Validators.pattern(/^#[a-z0-9]{3,8}$/)],
        this.validateFromAPI.bind(this),
      ],
      colorName: ['', [Validators.required], this.validateFromAPI.bind(this)],
    });

    this.colorId = this.route.snapshot.paramMap?.get('colorId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.colorId != null) {
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

  loadDataToUpdate(): void {
    this.colorService.getColorById(this.colorId).subscribe(
      (respon) => {
        this.formAddColor.patchValue({
          colorName: respon.colorName,
          code: respon.code,
        });
      },
      (error) => {
        this.toastr.error('không tìm thấy màu : ' + this.colorId);
      }
    );
  }

  addColor(color: Color) {
    this.colorService.postColor(color).subscribe(
      (res) => {
        this.toastr.success('thêm thành công màu : ' + res.colorName);
        this.tableService.notifyCountValue(res.colorId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/color');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi màu : ' + err.colorName);
        }, 1000);
      }
    );
  }

  updateColor(color: Color) {
    let pageNumber!: number;
    this.colorService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.colorService.updateColor(this.colorId, color).subscribe(
      (res) => {
        this.toastr.success('sửa thành công màu : ');
        this.tableService.notifyCountValue(res.colorId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/color/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi danh mục : ' + err.colorName);
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
      this.color = <Color>form.value;
      if (this.checkAction === 0) {
        this.addColor(this.color);
      }
      if (this.checkAction === 1) {
        this.updateColor(this.color);
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
    return this.colorService.getColorByFiled(control.value, filed).pipe(
      map((isValid) => {
        if (isValid.length === 0) {
          return null;
        }
        if (isValid.length !== 0 && this.colorId == isValid.colorId) {
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
