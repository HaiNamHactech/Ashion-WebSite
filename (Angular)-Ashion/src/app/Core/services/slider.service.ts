import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SLIDER_API } from '../common/baseAPI';
import { token } from '../common/token';
import { Slider } from '../models/slider';

@Injectable({
  providedIn: 'root',
})
export class SliderService {
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
    return this.httpClient.get(SLIDER_API,this.header);
  }

  public getById(sliderId: number): Observable<any> {
    return this.httpClient.get(SLIDER_API + '/' + sliderId,this.header);
  }

  public getSliderPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(SLIDER_API + '/paginations?filter=' + filter,this.header);
  }

  public add(slider: Slider): Observable<any> {
    return this.httpClient.post(SLIDER_API, slider,this.header);
  }

  public update(sliderId: number, slider: Slider) :Observable<any> {
    return this.httpClient.put(SLIDER_API + '/' + sliderId, slider,this.header);
  }

  public delete(sliderId: number): Observable<any> {
    return this.httpClient.delete(SLIDER_API + '/' + sliderId,this.header);
  }

}
