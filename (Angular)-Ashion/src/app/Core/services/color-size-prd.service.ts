import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CSP_API } from '../common/baseAPI';
import { token } from '../common/token';
import { ColorSizePrd } from '../models/colorSizePrd';

@Injectable({
  providedIn: 'root',
})
export class ColorSizePrdService {
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
    headers: new HttpHeaders().set(
      'Authorization',
      `Bearer ${this.codeToken} `
    ),
  };

  public getAll(): Observable<any> {
    return this.httpClient.get(CSP_API,this.header);
  }

  public getColorSizePagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(CSP_API + '/paginations?filter=' + filter,this.header);
  }

  public getById(id: number): Observable<any> {
    return this.httpClient.get(CSP_API + '/' + id,this.header);
  }

  public updateCSP(id: any, csp: ColorSizePrd): Observable<any> {
    return this.httpClient.put(CSP_API + '/' + id, csp,this.header);
  }

  public postCSP(listCSP: ColorSizePrd[]): Observable<any> {
    return this.httpClient.post(CSP_API, listCSP,this.header);
  }

  public deleteCSP(id: any): Observable<any> {
    return this.httpClient.delete(CSP_API + '/' + id,this.header);
  }
}
