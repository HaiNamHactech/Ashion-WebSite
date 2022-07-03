import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { COLOR_API } from '../common/baseAPI';
import { token } from '../common/token';

@Injectable({
  providedIn: 'root',
})
export class ColorService {
  private pageNumber = new BehaviorSubject('');
  private codeToken = new token().codeToken;

  header = {
    headers: new HttpHeaders().set(
      'Authorization',
      `Bearer ${this.codeToken} `
    ),
  };

  public get ValuePageNumber() {
    return this.pageNumber;
  }

  public notifyPageNumberValue(number: any) {
    this.pageNumber.next(number);
  }
  constructor(private httpClient: HttpClient) {}

  public getAllColor(): Observable<any> {
    return this.httpClient.get(COLOR_API, this.header);
  }

  public getColorPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      COLOR_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public getColorById(colorId: any): Observable<any> {
    return this.httpClient.get(COLOR_API + '/' + colorId, this.header);
  }

  public postColor(color: any): Observable<any> {
    return this.httpClient.post(COLOR_API, color, this.header);
  }

  public deleteColor(colorId: any): Observable<any> {
    return this.httpClient.delete(COLOR_API + '/' + colorId, this.header);
  }

  public updateColor(colorId: any, color: any): Observable<any> {
    return this.httpClient.put(COLOR_API + '/' + colorId, color, this.header);
  }

  public getColorByFiled(param: any, query: string): Observable<any> {
    param = param.startsWith('#') ? param.split('#').join('') : param;
    return this.httpClient.get(`${COLOR_API}/${query}?${query}=${param}`,this.header);
  }
}
