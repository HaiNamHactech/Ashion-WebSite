import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { COMMENT_BLOG_API } from '../common/baseAPI';
import { token } from '../common/token';

@Injectable({
  providedIn: 'root'
})
export class CommentBlogService {

  private pageNumber = new BehaviorSubject('');
  private codeToken = new token().codeToken;

  public get ValuePageNumber() {
    return this.pageNumber;
  }

  public notifyPageNumberValue(number: any) {
    this.pageNumber.next(number);
  }

  constructor(private httpClient : HttpClient) { }
  header = {
    headers: new HttpHeaders().set(
      'Authorization',
      `Bearer ${this.codeToken} `
    ),
  };

  public getAll() : Observable<any> {
    return this.httpClient.get(COMMENT_BLOG_API,this.header);
  }

  public getCommentPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(COMMENT_BLOG_API + '/paginations?filter=' + filter,this.header);
  }

  public getById(commentId : number) : Observable<any> {
    return this.httpClient.get(COMMENT_BLOG_API + '/' + commentId,this.header);
  }

  public add(comment : Comment) :Observable<any> {
    return this.httpClient.post(COMMENT_BLOG_API,Comment,this.header);
  }

  public delete(commentId : number) : Observable<any> {
    return this.httpClient.delete(COMMENT_BLOG_API + '/' + commentId,this.header);
  }

  public update(commentId : number , comment : Comment) : Observable<any> {
    return this.httpClient.put(COMMENT_BLOG_API + '/' + commentId,comment,this.header);
  }

}
