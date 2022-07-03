import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

import { AUTH_API } from '../common/baseAPI';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {
  userAccount = new EventEmitter<any>();
  constructor(private httpClient: HttpClient) {}

  public getToken(account: any): Observable<any> {
    return this.httpClient.post(AUTH_API, account);
  }
}
