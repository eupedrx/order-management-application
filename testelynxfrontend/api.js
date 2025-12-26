const API_BASE_URL = 'http://localhost:8080/api';

const API = {
    async fetchProducts() {
        const response = await fetch(`${API_BASE_URL}/products`);
        if (!response.ok) throw new Error('Erro ao buscar produtos');
        return response.json();
    },

    async createOrder(orderData) {
        const response = await fetch(`${API_BASE_URL}/orders`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderData)
        });
        if (!response.ok) throw new Error('Erro ao criar pedido');
        return response.json();
    },

    async getOrder(orderId) {
        const response = await fetch(`${API_BASE_URL}/orders/${orderId}`);
        if (!response.ok) throw new Error('Erro ao buscar pedido');
        return response.json();
    },

    async processPayment(paymentData) {
        const response = await fetch(`${API_BASE_URL}/payments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(paymentData)
        });
        if (!response.ok) throw new Error('Erro ao processar pagamento');
        return response.json();
    }
};
