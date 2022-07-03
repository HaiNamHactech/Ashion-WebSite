import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { USER_API } from '../common/baseAPI';
import { token } from '../common/token';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pageNumber = new BehaviorSubject('');
  private codeToken = new token().codeToken;

  public get ValuePageNumber() {
    return this.pageNumber;
  }

  public notifyPageNumberValue(number: any) {
    this.pageNumber.next(number);
  }

  constructor(private httpClient: HttpClient) {}

  header = {
    headers: new HttpHeaders().set('Authorization', `Bearer ${this.codeToken}`),
  };

  public getAll(): Observable<any> {
    return this.httpClient.get(USER_API, this.header);
  }

  public getById(userId: number): Observable<any> {
    return this.httpClient.get(USER_API + '/' + userId, this.header);
  }

  public getUserPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      USER_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public add(user: User): Observable<any> {
    return this.httpClient.post(USER_API, user, this.header);
  }

  public update(userId: number, user: User): Observable<any> {
    return this.httpClient.put(USER_API + '/' + userId, user, this.header);
  }

  public delete(userId: number): Observable<any> {
    return this.httpClient.delete(USER_API + '/' + userId, this.header);
  }
}
