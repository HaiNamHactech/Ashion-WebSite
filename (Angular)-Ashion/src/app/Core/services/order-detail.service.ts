import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ORDER_DETAIL_API } from '../common/baseAPI';
import { token } from '../common/token';
import { OrderDetail } from '../models/order-detail';

@Injectable({
  providedIn: 'root',
})
export class OrderDetailService {
  private codeToken = new token().codeToken;

  header = {
    headers: new HttpHeaders().set('Authorization', `Bearer ${this.codeToken}`),
  };

  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<any> {
    return this.httpClient.get(ORDER_DETAIL_API);
  }

  public getOrderDetailPagination(filter: any): Observable<any> {
    filter = JSON.stringify(filter);
    return this.httpClient.get(
      ORDER_DETAIL_API + '/paginations?filter=' + filter
    );
  }

  public getById(orderDetailId: number): Observable<any> {
    return this.httpClient.get(ORDER_DETAIL_API + '/' + orderDetailId);
  }

  public pushOrderDetail(listOrderDetail: OrderDetail[]): Observable<any> {
    return this.httpClient.post(ORDER_DETAIL_API, listOrderDetail, this.header);
  }

  public deleteOrderDetail(orderDetailId: number): Observable<any> {
    return this.httpClient.delete(
      ORDER_DETAIL_API + '/' + orderDetailId,
      this.header
    );
  }

  public updateOrderDetail(
    orderDetailId: number,
    orderDetail: OrderDetail
  ): Observable<any> {
    return this.httpClient.put(
      ORDER_DETAIL_API + '/' + orderDetailId,
      orderDetail,
      this.header
    );
  }
}
