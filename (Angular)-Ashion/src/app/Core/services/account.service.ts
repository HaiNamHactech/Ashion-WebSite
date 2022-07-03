import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { USER_API } from '../common/baseAPI';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private httpClient: HttpClient) {}

  public addAccount(account: any): Observable<any> {
    return this.httpClient.post(USER_API, account);
  }

  public getByFirstName(firstName: string): Observable<any> {
    return this.httpClient.get(USER_API + '/firstName?firstName=' + firstName);
  }

  public getByEmail(email: string): Observable<any> {
    return this.httpClient.get(USER_API + '/email?email=' + email);
  }

  public getUserByFiled(param: any,query:string):Observable<any>{
    return this.httpClient.get(`${USER_API}/${query}?${query}=${param}`);
  }
}
