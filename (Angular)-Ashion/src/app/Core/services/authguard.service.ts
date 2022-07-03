import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { token } from '../common/token';

@Injectable({
  providedIn: 'root',
})
export class AuthguardService {
  constructor(private route: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const decodedToken = new JwtHelperService().decodeToken(
      new token().codeToken
    );
    decodedToken?.role.map((role: any): any => {
      if (role?.authority !== 'ROLE_ADMIN') {
        this.route.navigate(['home']);
        return false;
      }
    });
    if (decodedToken === null || decodedToken?.role === null) {
      this.route.navigate(['home']);
      return false;
    }
    return true;
  }
}
