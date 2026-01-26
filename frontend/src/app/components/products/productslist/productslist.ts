import { Component, inject } from '@angular/core';
import { ProductFilterModel } from '../../../models/products/product-filter.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductsService } from '../../../services/products-service';
import { OrdersService } from '../../../services/orders-service';
import { PaymentsService } from '../../../services/payments-service';
import { CreateOrderItemModel } from '../../../models/orders/create-order-item.model';
import { CreateOrderModel } from '../../../models/orders/create-order.model';
import { PaymentRequest } from '../../../models/payments/payment-request';
import { PaymentMethod } from '../../../models/enums/payment-method';

@Component({
  selector: 'app-productslist',
  imports: [CommonModule, FormsModule],
  templateUrl: './productslist.html',
  styleUrl: './productslist.css',
})
export class Productslist {
  lista: ProductFilterModel[] = [];

  // categorias distintas vindas dos produtos
  categorias: string[] = [];

  // categoria selecionada no filtro
  categoriaSelecionada: string = '';

  // Controle do filtro "Somente Ativos"
  onlyActive = true;

  // Quantidade selecionada por produto (id -> quantidade)
  quantities: { [productId: number]: number } = {};

  // Itens do carrinho
  cart: {
    productId: number;
    name: string;
    priceCents: number;
    quantity: number;
  }[] = [];

  // controle do modal de carrinho na mesma rota
  isCartModalOpen = false;

  // controle do modal de checkout
  isCheckoutOpen = false;

  // campos do checkout
  customerId: number = 1;
  paymentMethod: PaymentMethod | '' = '';
  paymentAmount: number | null = null;

  productsService = inject(ProductsService);
  ordersService = inject(OrdersService);
  paymentsService = inject(PaymentsService);

  PaymentMethod = PaymentMethod;

  constructor() {
    this.listarProdutos();
    this.loadCartFromStorage();
  }

  listarProdutos() {
    this.productsService.listarProdutos().subscribe({
      next: (produtos) => {
        this.lista = produtos;
        this.categorias = Array.from(
          new Set(
            (produtos || [])
              .map((p) => p.category || '')
              .filter((c) => c && c.trim().length > 0)
          )
        ).sort();
      },
      error: (erro) => {
        console.error('Erro ao listar produtos:', erro);
      },
    });
  }

  onQuantityChange(productId: number, value: number | null) {
    const quantity = value ?? 1;
    this.quantities[productId] = quantity > 0 ? quantity : 1;
  }

  addToCart(product: ProductFilterModel) {
    if (!product.id || !product.name || product.priceCents == null) {
      return;
    }

    if (!this.isProductAvailable(product)) {
      alert('Produto indisponível para adicionar ao carrinho');
      return;
    }

    const quantity = this.quantities[product.id] ?? 1;

    if (quantity <= 0) {
      alert('Quantidade deve ser maior que zero');
      return;
    }

    if (product.stock != null && quantity > product.stock) {
      alert(`Estoque insuficiente. Disponível: ${product.stock}`);
      return;
    }

    const existingItem = this.cart.find((item) => item.productId === product.id);
    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      this.cart.push({
        productId: product.id,
        name: product.name,
        priceCents: product.priceCents,
        quantity,
      });
    }

    this.saveCartToStorage();
    this.quantities[product.id] = 1;
  }

  removeFromCart(index: number) {
    this.cart.splice(index, 1);
    this.saveCartToStorage();
  }

  get cartTotalCents(): number {
    return this.cart.reduce(
      (sum, item) => sum + item.priceCents * item.quantity,
      0
    );
  }

  get cartTotalFormatted(): string {
    return `R$ ${(this.cartTotalCents / 100).toFixed(2)}`;
  }

  get cartCount(): number {
    return this.cart.reduce((sum, item) => sum + item.quantity, 0);
  }

  get filteredProducts(): ProductFilterModel[] {
    let produtos = this.lista;

    if (this.onlyActive) {
      produtos = produtos.filter((p) => p.active !== false);
    }

    if (this.categoriaSelecionada) {
      produtos = produtos.filter((p) => p.category === this.categoriaSelecionada);
    }

    return produtos;
  }

  private saveCartToStorage() {
    if (typeof localStorage === 'undefined') {
      return;
    }
    localStorage.setItem('cart', JSON.stringify(this.cart));
  }

  private loadCartFromStorage() {
    if (typeof localStorage === 'undefined') {
      return;
    }
    const stored = localStorage.getItem('cart');
    if (stored) {
      try {
        this.cart = JSON.parse(stored);
      } catch {
        this.cart = [];
      }
    }
  }

  isProductAvailable(product: ProductFilterModel): boolean {
    if (product.active === false) {
      return false;
    }

    if (product.stock != null && product.stock <= 0) {
      return false;
    }

    return true;
  }

  openCartModal() {
    this.isCartModalOpen = true;
  }

  closeCartModal() {
    this.isCartModalOpen = false;
  }

  abrirCheckout() {
    if (this.cart.length === 0) {
      return;
    }
    this.isCartModalOpen = false;
    this.isCheckoutOpen = true;
  }

  fecharCheckout() {
    this.isCheckoutOpen = false;
  }

  confirmarPedido() {
    if (this.cart.length === 0) {
      alert('Carrinho vazio. Adicione itens antes de finalizar.');
      return;
    }

    if (!this.paymentMethod || this.paymentAmount == null || this.paymentAmount <= 0) {
      alert('Preencha método de pagamento e valor válido.');
      return;
    }

    const items = this.cart.map(
      (item) => new CreateOrderItemModel(item.productId, item.quantity)
    );

    const orderRequest = new CreateOrderModel(this.customerId, items);

    this.ordersService.criarOrder(orderRequest).subscribe({
      next: (order) => {
        const amountCents = Math.round(this.paymentAmount! * 100);
        const paymentRequest = new PaymentRequest(
          order.id,
          this.paymentMethod as PaymentMethod,
          amountCents
        );

        this.paymentsService.proccessPayment(paymentRequest).subscribe({
          next: () => {
            alert('Pedido e pagamento processados com sucesso!');
            this.cart = [];
            this.saveCartToStorage();
            this.paymentMethod = '';
            this.paymentAmount = null;
            this.isCheckoutOpen = false;
          },
          error: (err) => {
            console.error('Erro ao processar pagamento', err);
            alert('Erro ao processar pagamento.');
          },
        });
      },
      error: (err) => {
        console.error('Erro ao criar pedido', err);
        alert('Erro ao criar pedido.');
      },
    });
  }
}
