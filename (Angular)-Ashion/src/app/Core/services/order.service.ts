import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ORDER_API } from '../common/baseAPI';
import { token } from '../common/token';
import { Order } from '../models/order';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private codeToken = new token().codeToken;

  header = {
    headers: new HttpHeaders().set(
      'Authorization',
      `Bearer ${this.codeToken} `
    ),
  };

  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<any> {
    return this.httpClient.get(ORDER_API);
  }

  public getOrderPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(ORDER_API + '/paginations?filter=' + filter);
  }

  public getById(orderId: number): Observable<any> {
    return this.httpClient.get(ORDER_API + '/' + orderId);
  }

  public pushOrder(order: Order): Observable<any> {
    return this.httpClient.post(ORDER_API, order, this.header);
  }

  public deleteOrder(orderId: number): Observable<any> {
    return this.httpClient.delete(ORDER_API + '/' + orderId, this.header);
  }

  public updateOrder(orderId: number, order: Order): Observable<any> {
    return this.httpClient.put(ORDER_API + '/' + orderId, order, this.header);
  }
}
