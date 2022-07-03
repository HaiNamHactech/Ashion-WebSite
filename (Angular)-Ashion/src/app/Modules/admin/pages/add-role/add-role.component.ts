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
import { Role } from 'src/app/Core/models/role';
import { RoleService } from 'src/app/Core/services/role.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-role',
  templateUrl: './add-role.component.html',
  styleUrls: ['./add-role.component.css'],
})
export class AddRoleComponent implements OnInit {
  public formAddRole!: FormGroup;
  public roleId!: any;
  public action!: any;
  public checkAction: any = 0;
  public checkLoadding: boolean = false;
  public role!: Role;

  constructor(
    private fb: FormBuilder,
    private roleService: RoleService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private _router: Router,
    private tableService: TableService
  ) {}

  ngOnInit(): void {
    this.formAddRole = this.fb.group({
      name: ['', [Validators.required], this.validateFromAPI.bind(this)],
    });

    this.roleId = this.route.snapshot.paramMap?.get('roleId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.roleId != null) {
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
    this.roleService.getById(this.roleId).subscribe(
      (respon) => {
        this.formAddRole.patchValue({
          name: respon.name,
        });
      },
      (error) => {
        this.toastr.error('không tìm thấy role :');
      }
    );
  }

  addRole(role: Role) {
    this.roleService.add(role).subscribe(
      (res) => {
        this.toastr.success('thêm thành công role : ' + res.roleId);
        this.tableService.notifyCountValue(res.roleId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/role');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi role : ' + err.name);
        }, 1000);
      }
    );
  }

  updateRole(role: Role) {
    let pageNumber!: number;
    this.roleService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.roleService.update(this.roleId, role).subscribe(
      (res) => {
        this.toastr.success('sửa thành công role : ');
        this.tableService.notifyCountValue(res.roleId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/role/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi role : ' + err.roleId);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    this.role = <Role>form.value;
    if (this.checkAction === 0) {
      this.addRole(this.role);
    }
    if (this.checkAction === 1) {
      this.updateRole(this.role);
    }
  }

  validateFromAPI(
    control: AbstractControl
  ): Observable<ValidationErrors | null> {
    if (this.checkAction === 2) {
      return of(null);
    }
    return this.roleService.getByRoleName(control.value).pipe(
      map((roleName) => {
        if (roleName.length === 0) {
          return null;
        }
        if (roleName.length !== 0 && this.roleId == roleName.roleId) {
          return null;
        }
        return {
          RoleNameFiledDuplicated: true,
        };
      }),
      catchError(() => {
        return EMPTY;
      })
    );
  }
}
