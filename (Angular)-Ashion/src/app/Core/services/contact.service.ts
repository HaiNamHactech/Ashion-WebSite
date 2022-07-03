import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CONTACT_API } from '../common/baseAPI';
import { token } from '../common/token';
import { Contact } from '../models/contact';

@Injectable({
  providedIn: 'root',
})
export class ContactService {
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
    return this.httpClient.get(CONTACT_API,this.header);
  }

  public getContactPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(CONTACT_API + '/paginations?filter=' + filter,this.header);
  }

  public getById(contactId: number): Observable<any> {
    return this.httpClient.get(CONTACT_API + '/' + contactId,this.header);
  }

  public add(contact: Contact): Observable<any> {
    return this.httpClient.post(CONTACT_API, contact,this.header);
  }

  public update(contactId: number, contact: Contact): Observable<any> {
    return this.httpClient.put(CONTACT_API + '/' + contactId, contact,this.header);
  }

  public delete(contactId: number): Observable<any> {
    return this.httpClient.delete(CONTACT_API + '/' + contactId,this.header);
  }
  
}
