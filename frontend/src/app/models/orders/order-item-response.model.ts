export class OrderItemResponseModel {
  constructor(
    public quantity: number,
    public unitPriceCents: number,
    public product: { id: number; name: string; category: string; priceCents: number; active: boolean }, //Tipo do produto
    public id: number,
    public subtotal: number
  ) {}

  // Getters para facilitar o acesso aos dados do produto
  get productName(): string {
    return this.product?.name ?? '';
  }

  get priceCents(): number {
    return this.unitPriceCents;
  }

  get productId(): number {
    return this.product?.id ?? 0;
  }
}