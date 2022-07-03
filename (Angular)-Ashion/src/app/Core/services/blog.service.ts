import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { BLOG_API, UPLOAD_IMAGE } from '../common/baseAPI';
import { token } from '../common/token';
import { Blog } from '../models/blog';

@Injectable({
  providedIn: 'root',
})
export class BlogService {
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
    formData.append('upload_preset', 'category-blog');
    return this.httpClient.post(UPLOAD_IMAGE, formData);
  }

  public getAll(): Observable<any> {
    return this.httpClient.get(BLOG_API, this.header);
  }
  public getBlogPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      BLOG_API + '/paginations?filter=' + filter,
      this.header
    );
  }

  public getById(blogId: number): Observable<any> {
    return this.httpClient.get(BLOG_API + '/' + blogId, this.header);
  }

  public update(blogId: number, blog: Blog): Observable<any> {
    return this.httpClient.put(BLOG_API + '/' + blogId, blog, this.header);
  }

  public deleteBlog(blogId: number): Observable<any> {
    return this.httpClient.delete(BLOG_API + '/' + blogId, this.header);
  }

  public add(blog: Blog): Observable<any> {
    return this.httpClient.post(BLOG_API, blog, this.header);
  }
}
