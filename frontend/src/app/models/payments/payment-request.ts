
import { PaymentMethod } from '../enums/payment-method';

export class PaymentRequest {

constructor(
    public orderId: number,
    public method: PaymentMethod,
    public amountCents: number
  ) {}

}
