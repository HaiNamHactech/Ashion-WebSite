import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { INSTAGRAM_API, UPLOAD_IMAGE } from '../common/baseAPI';
import { token } from '../common/token';
import { Instagram } from '../models/instagram';

@Injectable({
  providedIn: 'root',
})
export class InstagramService {
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

  public upload(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', 'instagram');
    return this.httpClient.post(UPLOAD_IMAGE, formData);
  }

  public getAll(): Observable<any> {
    return this.httpClient.get(INSTAGRAM_API, this.header);
  }

  public getInstaPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      INSTAGRAM_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public getById(instaId: number): Observable<any> {
    return this.httpClient.get(INSTAGRAM_API + '/' + instaId, this.header);
  }

  public add(insta: Instagram): Observable<any> {
    return this.httpClient.post(INSTAGRAM_API, insta, this.header);
  }

  public update(instaId: number, insta: Instagram): Observable<any> {
    return this.httpClient.put(
      INSTAGRAM_API + '/' + instaId,
      insta,
      this.header
    );
  }

  public delete(instaId: number): Observable<any> {
    return this.httpClient.delete(INSTAGRAM_API + '/' + instaId, this.header);
  }
}
