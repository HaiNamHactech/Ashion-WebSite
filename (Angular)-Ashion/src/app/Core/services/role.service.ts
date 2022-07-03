import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ROLE_API } from '../common/baseAPI';
import { Role } from '../models/role';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  private pageNumber = new BehaviorSubject('');

  public get ValuePageNumber() {
    return this.pageNumber;
  }

  public notifyPageNumberValue(number: any) {
    this.pageNumber.next(number);
  }

  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<any> {
    return this.httpClient.get(ROLE_API);
  }

  public getRolePagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(ROLE_API + '/paginations?filter=' + filter);
  }

  public getById(roleId: number): Observable<any> {
    return this.httpClient.get(ROLE_API + '/' + roleId);
  }

  public add(role: Role): Observable<any> {
    return this.httpClient.post(ROLE_API, role);
  }

  public delete(roleId: number): Observable<any> {
    return this.httpClient.delete(ROLE_API + '/' + roleId);
  }

  public update(roleId: number, role: Role): Observable<any> {
    return this.httpClient.put(ROLE_API + '/' + roleId, role);
  }

  public getByRoleName(roleName: string): Observable<any> {
    return this.httpClient.get(ROLE_API + '/name?name=' + roleName);
  }
}
