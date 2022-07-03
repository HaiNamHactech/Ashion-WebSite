import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SIZE_API } from '../common/baseAPI';
import { token } from '../common/token';

@Injectable({
  providedIn: 'root',
})
export class SizeService {
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

  public getAllSize(): Observable<any> {
    return this.httpClient.get(SIZE_API, this.header);
  }

  public getSizePagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      SIZE_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public getSizeById(SizeId: number): Observable<any> {
    return this.httpClient.get(SIZE_API + '/' + SizeId, this.header);
  }

  public postSize(Size: any): Observable<any> {
    return this.httpClient.post(SIZE_API, Size, this.header);
  }

  public deleteSize(SizeId: number): Observable<any> {
    return this.httpClient.delete(SIZE_API + '/' + SizeId, this.header);
  }

  public updateSize(SizeId: number, Size: any): Observable<any> {
    return this.httpClient.put(SIZE_API + '/' + SizeId, Size, this.header);
  }

  public getBySizeName(sizeName: string): Observable<any> {
    return this.httpClient.get(
      SIZE_API + '/sizeName?sizeName=' + sizeName,
      this.header
    );
  }

}
