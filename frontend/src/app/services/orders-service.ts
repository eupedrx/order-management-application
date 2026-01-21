import { HttpClient } from '@angular/common/http';
import { Injectable, Inject, inject } from '@angular/core';
import { OrderResponseModel } from '../models/orders/order-response.model';
import { OrderItemResponseModel } from '../models/orders/order-item-response.model';
import { map } from 'rxjs';
import { CreateOrderModel } from '../models/orders/create-order.model';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  http = inject(HttpClient);

  API = "http://localhost:8080/api/orders"

 listarOrders() {
  return this.http.get<any[]>(this.API).pipe(  // Utilizando .pipe para transformar os dados
    map(ordersJson =>
      ordersJson.map(o =>
        new OrderResponseModel(
          o.id,
          o.status,
          o.totalCents,
          new Date(o.createdAt),
          o.customerId,
          o.items.map((i: any) =>
            new OrderItemResponseModel(
              i.quantity,
              i.unitPriceCents,
              i.product,
              i.id,
              i.subtotal
            )
          )
        )
      )
    )
  );
 }

 criarOrder(order: CreateOrderModel) {
  return this.http.post<any>(this.API, order).pipe(
    map(o =>
      new OrderResponseModel(
        o.id,
        o.status,
        o.totalCents,
        new Date(o.createdAt),
        o.customerId,
        o.items.map((i: any) =>
          new OrderItemResponseModel(
            i.quantity,
            i.unitPriceCents,
            i.product,
            i.id,
            i.subtotal
          )
        )
      )
    )
  );
 }

 cancelarOrder(orderId: number) {
  const url = `${this.API}/${orderId}`;
  return this.http.put<any>(url, {});
 }
}