import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CATEGORY_BLOB_API } from '../common/baseAPI';
import { token } from '../common/token';

@Injectable({
  providedIn: 'root',
})
export class CategoryBlogService {
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

  public getAllCategoryBlog(): Observable<any> {
    return this.httpClient.get(CATEGORY_BLOB_API, this.header);
  }

  public getCategoryBlogById(cateId: any): Observable<any> {
    return this.httpClient.get(CATEGORY_BLOB_API + '/' + cateId, this.header);
  }

  public getCategoryByFiled(param: any, query: string): Observable<any> {
    return this.httpClient.get(
      `${CATEGORY_BLOB_API}/${query}?${query}=${param}`,this.header
    );
  }

  public getCategoryPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      CATEGORY_BLOB_API + '/paginations?filter=' + filter,
      this.header
    );
  }
  public postCategoryBlog(catrgoryBlogDto: any): Observable<any> {
    return this.httpClient.post(
      CATEGORY_BLOB_API,
      catrgoryBlogDto,
      this.header
    );
  }

  public deleteCategoryBlog(cateId: any): Observable<any> {
    return this.httpClient.delete(
      CATEGORY_BLOB_API + '/' + cateId,
      this.header
    );
  }

  public updateCategoryBlog(
    cateId: any,
    categoryBlogDto: any
  ): Observable<any> {
    return this.httpClient.put(
      CATEGORY_BLOB_API + '/' + cateId,
      categoryBlogDto,
      this.header
    );
  }
}
