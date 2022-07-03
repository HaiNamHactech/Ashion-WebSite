import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CATEGORY_PRODUCT_API, UPLOAD_IMAGE } from '../common/baseAPI';
import { token } from '../common/token';

@Injectable({
  providedIn: 'root',
})
export class CategoryProductService {
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
    formData.append('upload_preset', 'category');
    return this.httpClient.post(UPLOAD_IMAGE, formData, this.header);
  }

  public getAllCategoryPrd(): Observable<any> {
    return this.httpClient.get(CATEGORY_PRODUCT_API, this.header);
  }

  public getCategoryPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      CATEGORY_PRODUCT_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public getCategoryPrdById(cateId: any): Observable<any> {
    return this.httpClient.get(
      CATEGORY_PRODUCT_API + '/' + cateId,
      this.header
    );
  }

  public postCategoryPrd(categoryPrdDto: any): Observable<any> {
    return this.httpClient.post(
      CATEGORY_PRODUCT_API,
      categoryPrdDto,
      this.header
    );
  }

  public getCategoryByFiled(param: any, query: string): Observable<any> {
    return this.httpClient.get(
      `${CATEGORY_PRODUCT_API}/${query}?${query}=${param}`,
      this.header
    );
  }

  public deleteCategoryPrd(cateId: any): Observable<any> {
    return this.httpClient.delete(
      CATEGORY_PRODUCT_API + '/' + cateId,
      this.header
    );
  }

  public updateCategoryPrd(cateId: any, categoryPrdDto: any): Observable<any> {
    return this.httpClient.put(
      CATEGORY_PRODUCT_API + '/' + cateId,
      categoryPrdDto,
      this.header
    );
  }
}
