import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { PRD_IMAGE, UPLOAD_IMAGE } from '../common/baseAPI';
import { token } from '../common/token';
import { Image } from '../models/image';

@Injectable({
  providedIn: 'root',
})
export class ImagePrdService {
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
    formData.append('upload_preset', 'product');
    return this.httpClient.post(UPLOAD_IMAGE, formData);
  }

  public getAll(): Observable<any> {
    return this.httpClient.get(PRD_IMAGE, this.header);
  }

  public getById(imageId: number): Observable<any> {
    return this.httpClient.get(PRD_IMAGE + '/' + imageId, this.header);
  }

  public getImagePagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      PRD_IMAGE + '/paginations?filter=' + filter,
      this.header
    );
  }

  public add(image: Image): Observable<any> {
    return this.httpClient.post(PRD_IMAGE, image, this.header);
  }

  public delete(imageId: number): Observable<any> {
    return this.httpClient.delete(PRD_IMAGE + '/' + imageId, this.header);
  }

  public update(imageId: number, image: Image): Observable<any> {
    return this.httpClient.put(PRD_IMAGE + '/' + imageId, image, this.header);
  }
}
