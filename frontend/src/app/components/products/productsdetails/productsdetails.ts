import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-productsdetails',
  imports: [CommonModule],
  templateUrl: './productsdetails.html',
  styleUrl: './productsdetails.css',
})
export class Productsdetails {

  isCartModalOpen = true;

  cart: {
    productId: number;
    name: string;
    priceCents: number;
    quantity: number;
  }[] = [];

  constructor() {
    this.loadCartFromStorage();
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

  closeCartModal() {
    this.isCartModalOpen = false;
  }

  removeFromCart(index: number) {
    this.cart.splice(index, 1);
    this.saveCartToStorage();
  }

  private loadCartFromStorage() {
    const stored = localStorage.getItem('cart');
    if (stored) {
      try {
        this.cart = JSON.parse(stored);
      } catch {
        this.cart = [];
      }
    }
  }

  private saveCartToStorage() {
    localStorage.setItem('cart', JSON.stringify(this.cart));
  }

}
