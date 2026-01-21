import { OrderItemResponseModel } from "./order-item-response.model";

export class OrderResponseModel {

    constructor(
    public id: number,
    public status: string,
    public totalCents: number,
    public createdAt: Date,
    public customerId: number,
    public items: OrderItemResponseModel[]
  ) {}

}
