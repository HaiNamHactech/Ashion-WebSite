import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { USER_ROLE_API } from '../common/baseAPI';
import { token } from '../common/token';
import { UserRole } from '../models/user-role';

@Injectable({
  providedIn: 'root',
})
export class UserRoleService {
  private codeToken = new token().codeToken;
  private pageNumber = new BehaviorSubject('');
  constructor(private httpClient: HttpClient) {}
  public get ValuePageNumber() {
    return this.pageNumber;
  }

  public notifyPageNumberValue(number: any) {
    this.pageNumber.next(number);
  }

  header = {
    headers: new HttpHeaders().set('Authorization', `Bearer ${this.codeToken}`),
  };

  public getAll(): Observable<any> {
    return this.httpClient.get(USER_ROLE_API, this.header);
  }

  public getById(id: number): Observable<any> {
    return this.httpClient.get(USER_ROLE_API + '/' + id, this.header);
  }

  public getUserRolePagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      USER_ROLE_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public add(userRole: UserRole): Observable<any> {
    return this.httpClient.post(USER_ROLE_API, userRole, this.header);
  }

  public update(id: number, userRole: UserRole): Observable<any> {
    return this.httpClient.put(USER_ROLE_API + '/' + id, UserRole, this.header);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete(USER_ROLE_API + '/' + id, this.header);
  }
}
