import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EMPTY, Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { AccountService } from 'src/app/Core/services/account.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  public formRegister!: FormGroup;
  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.formRegister = this.fb.group({
      firstName: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]{6,32}$/i)],
        this.validateFromAPI.bind(this),
      ],
      lastName: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]{6,32}$/i)],
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(/^[\w\.]+@([\w-]+\.)+[\w-]{2,4}/),
        ],
        this.validateFromAPI.bind(this),
      ],
      passWord: [
        '',
        [
          Validators.required,
          Validators.pattern(/^(?=.*[!@#$%^&*]+)[a-z0-9!@#$%^&*]{6,32}$/),
        ],
      ],
    });
  }

  submitForm(form: FormGroup) {
    this.accountService.addAccount(form.value).subscribe((res) => {
      console.log();

      this.toastr.success('thêm thành công màu : ' + res.firstName);
      setTimeout(() => {
        this.router.navigateByUrl('/login');
      }, 1000);
    });
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
    let ok = this.accountService.getUserByFiled(control.value, filed).pipe(
      map((isValid) => {
        if (isValid.length === 0) {
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
    return ok;
  }
}
