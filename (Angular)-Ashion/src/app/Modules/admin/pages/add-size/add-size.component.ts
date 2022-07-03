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
import { Size } from 'src/app/Core/models/size';
import { SizeService } from 'src/app/Core/services/size.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-size',
  templateUrl: './add-size.component.html',
  styleUrls: ['./add-size.component.css'],
})
export class AddSizeComponent implements OnInit {
  public formAddSize!: FormGroup;
  public size!: Size;
  public sizeId!: any;
  public action!: any;
  public checkAction: any = 0;

  constructor(
    private fb: FormBuilder,
    private sizeService: SizeService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private tableService: TableService,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this.formAddSize = this.fb.group({
      sizeName: ['', [Validators.required], this.validateFromAPI.bind(this)],
      description: [''],
    });

    this.sizeId = this.route.snapshot.paramMap?.get('sizeId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.sizeId != null) {
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
    this.sizeService.getSizeById(this.sizeId).subscribe(
      (respon) => {
        this.formAddSize.patchValue({
          sizeName: respon.sizeName,
          description: respon.description,
        });
      },
      (error) => {
        this.toastr.error('không tìm thấy size : ' + this.sizeId);
      }
    );
  }

  addSize(size: Size) {
    this.sizeService.postSize(size).subscribe(
      (res) => {
        this.toastr.success('thêm thành công danh size : ' + res.sizeName);
        this.tableService.notifyCountValue(res.sizeId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/size');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi size : ' + err.sizeName);
        }, 1000);
      }
    );
  }

  updateSize(size: Size) {
    let pageNumber!: number;
    this.sizeService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.sizeService.updateSize(this.sizeId, size).subscribe(
      (res) => {
        this.toastr.success('sửa thành công size : ');
        this.tableService.notifyCountValue(res.sizeId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/size/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi danh mục : ' + err.sizeName);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    this.size = <Size>form.value;
    if (this.checkAction === 0) {
      this.addSize(this.size);
    }
    if (this.checkAction === 1) {
      this.updateSize(this.size);
    }
  }

  validateFromAPI(
    control: AbstractControl
  ): Observable<ValidationErrors | null> {
    if (this.checkAction === 2) {
      return of(null);
    }
    return this.sizeService.getBySizeName(control.value).pipe(
      map((sizeName) => {
        if (sizeName.length === 0) {
          return null;
        }
        if (sizeName.length !== 0 && this.sizeId == sizeName.roleId) {
          return null;
        }
        return {
          SizeNameFiledDuplicated: true,
        };
      }),
      catchError(() => {
        return EMPTY;
      })
    );
  }
}
