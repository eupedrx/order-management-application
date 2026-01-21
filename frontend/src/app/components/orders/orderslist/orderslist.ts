import { Component, inject } from '@angular/core';
import { OrderResponseModel } from '../../../models/orders/order-response.model';
import { OrdersService } from '../../../services/orders-service';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-orderslist',
  imports: [CommonModule, DatePipe],
  templateUrl: './orderslist.html',
  styleUrl: './orderslist.scss',
})
export class Orderslist {


  lista: OrderResponseModel[] = [];

  orderService = inject(OrdersService);

  selectedOrder: OrderResponseModel | null = null;


  constructor() {
    this.buscarOrders();
  }

  // Função para buscar os pedidos do serviço
  buscarOrders() {
  this.orderService.listarOrders().subscribe({
      next: (orders) => {
        this.lista = orders;
      },
      error: (erro) => {
        console.error('Erro ao buscar orders:', erro);
      }
    });
  }


  // Função para exibir os detalhes do pedido selecionado
  viewOrderDetails(order: OrderResponseModel) {
    this.selectedOrder = order;
  }

  // Função para fechar o modal de detalhes do pedido
  closeOrderDetails() {
    this.selectedOrder = null;
  }
  

  // Função para calcular o total do pedido somando os subtotais dos itens
  getOrderTotalCents(order: any): number {
  if (!order?.items) {
    return 0;
  }
  return order.items.reduce(
    (sum: number, item: any) => sum + (item.subtotal ?? 0),
    0
  );
}

 cancelarPedidoSelecionado() {
  if (!this.selectedOrder) {
    return;
  }

  const confirmar = confirm(`Deseja cancelar o pedido ${this.selectedOrder.id}?`);
  if (!confirmar) {
    return;
  }

  this.orderService.cancelarOrder(this.selectedOrder.id).subscribe({
    next: () => {
      this.lista = this.lista.map((o) =>
        o.id === this.selectedOrder!.id ? { ...o, status: 'CANCELADO' } : o
      );
      this.selectedOrder = {
        ...this.selectedOrder!,
        status: 'CANCELADO',
      };
      alert('Pedido cancelado com sucesso.');
    },
    error: (erro) => {
      console.error('Erro ao cancelar pedido:', erro);
      alert('Não foi possível cancelar o pedido.');
    },
  });
 }

}
