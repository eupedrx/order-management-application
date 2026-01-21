import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { PaymentRequest } from '../models/payments/payment-request';

@Injectable({
  providedIn: 'root',
})
export class PaymentsService {


  http = inject(HttpClient);

  API = "http://localhost:8080/api/payments";

  proccessPayment(request: PaymentRequest){
    return this.http.post<PaymentRequest>(this.API, request);
  }
  
}
