import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { AuthServiceService } from 'src/app/Core/services/auth-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public formLogin!: FormGroup;
  public messErrors!: string;
  constructor(
    private fb: FormBuilder,
    private authServiece: AuthServiceService,
    private route: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.formLogin = this.fb.group({
      userName: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]{6,32}$/i)],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.pattern(/^(?=.*[!@#$%^&*]+)[a-z0-9!@#$%^&*]{6,32}$/),
        ],
      ],
    });
  }

  submitForm(form: FormGroup) {
    this.authServiece.getToken(form.value).subscribe(
      (token) => {
        localStorage.setItem('token', JSON.stringify(token.code));
        const decodedToken = new JwtHelperService().decodeToken(token.code);
        this.authServiece.userAccount.emit(decodedToken?.userName);
        let checkAdmin = false;
        decodedToken?.role.map((role: any): any => {
          if (role?.authority === 'ROLE_ADMIN') {
            checkAdmin = true;
          }
        });
        this.toastr.success('Hello Member ' + decodedToken?.userName);
        if (checkAdmin) {
          setTimeout(() => {
            this.route.navigate(['admin']);
          }, 1000);
        } else {
          setTimeout(() => {
            this.route.navigate(['home']);
          }, 1000);
        }
        this.formLogin.reset();
      },
      (err) => {
        this.toastr.error('tài khoản hoặc mật khẩu sai !');
        this.messErrors = '( tài khoản  hoặc mật khẩu sai! )';
      }
    );
  }
}
