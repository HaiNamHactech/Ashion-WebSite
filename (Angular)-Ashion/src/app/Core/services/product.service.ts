import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { PRD_API, UPLOAD_IMAGE } from '../common/baseAPI';
import { token } from '../common/token';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
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

  public getAllProduct(): Observable<any> {
    return this.httpClient.get(PRD_API, this.header);
  }

  public getProductPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      PRD_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public getProductById(productId: number): Observable<any> {
    return this.httpClient.get(PRD_API + '/' + productId, this.header);
  }

  public getByProductName(productName: string): Observable<any> {
    return this.httpClient.get(
      PRD_API + '/productName?productName=' + productName,this.header
    );
  }

  public postProduct(product: any): Observable<any> {
    return this.httpClient.post(PRD_API, product, this.header);
  }

  public deleteProduct(productId: number): Observable<any> {
    return this.httpClient.delete(PRD_API + '/' + productId, this.header);
  }

  public updateProduct(productId: number, product: any): Observable<any> {
    return this.httpClient.put(PRD_API + '/' + productId, product, this.header);
  }

  
}
