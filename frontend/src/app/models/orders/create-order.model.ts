import { CreateOrderItemModel } from './create-order-item.model';

export class CreateOrderModel {
	constructor(
		public customerId: number,
		public items: CreateOrderItemModel[]
	) {}
}
