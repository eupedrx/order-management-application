const API_BASE_URL = 'http://localhost:8080/api';

const API = {


    // Buscar lista de produtos
    async fetchProducts() {
        const response = await fetch(`${API_BASE_URL}/products`);
        if (!response.ok) throw new Error('Erro ao buscar produtos');
        return response.json();
    },

    // Criar novo pedido
    async createOrder(orderData) {
        const response = await fetch(`${API_BASE_URL}/orders`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderData)
        });
        if (!response.ok) throw new Error('Erro ao criar pedido');
        return response.json();
    },

    // Buscar detalhes do pedido
    async getOrder(orderId) {
        const response = await fetch(`${API_BASE_URL}/orders/${orderId}`);
        if (!response.ok) throw new Error('Erro ao buscar pedido');
        return response.json();
    },

    // Processar pagamento
    async processPayment(paymentData) {
        const response = await fetch(`${API_BASE_URL}/payments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(paymentData)
        });
        if (!response.ok) {
            const errorText = await response.text();
            console.error('Erro do servidor:', errorText);
            throw new Error('Erro ao processar pagamento');
        }
        return response.json();
    },

    // Cancelar pedido
    async cancelOrder(id) {
        const response = await fetch(`${API_BASE_URL}/orders/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' }
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Erro ao cancelar pedido');
        }
        return response.json();
    }
};