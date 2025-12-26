// Estado da aplicação
let products = [];
let cart = JSON.parse(localStorage.getItem('cart')) || [];

document.addEventListener('DOMContentLoaded', async () => {
    await loadProducts();
    setupFilters();
    renderCart();
    updateCartCount();
});
// Carregar produtos da API
async function loadProducts() {
    try {
        products = await API.fetchProducts();
        loadCategories();
        renderProducts();
    } catch (error) {
        console.error('Erro ao carregar produtos:', error);
        const grid = document.getElementById('productsGrid');
        if (grid) {
            grid.innerHTML = '<p class="error">Erro ao carregar produtos.</p>';
        }
    }
}

// Carregar categorias únicas
function loadCategories() {
    const categories = [...new Set(products.map(p => p.category))];
    const select = document.getElementById('categoryFilter');
    
    if (!select) {
        console.error('Select de categoria não encontrado!');
        return;
    }
    
    // Limpar opções anteriores (exceto "Todas")
    select.innerHTML = '<option value="">Todas</option>';
    
    categories.forEach(cat => {
        const option = document.createElement('option');
        option.value = cat;
        option.textContent = cat;
        select.appendChild(option);
    });
}

// Configurar filtros
function setupFilters() {
    const searchInput = document.getElementById('searchInput');
    const categoryFilter = document.getElementById('categoryFilter');
    const activeToggle = document.getElementById('activeToggle');
    
    if (searchInput) {
        searchInput.addEventListener('input', renderProducts);
    }
    if (categoryFilter) {
        categoryFilter.addEventListener('change', renderProducts);
    }
    if (activeToggle) {
        activeToggle.addEventListener('change', renderProducts);
    }
}

// Renderizar produtos com filtros aplicados
function renderProducts() {
    const searchInput = document.getElementById('searchInput');
    const categoryFilter = document.getElementById('categoryFilter');
    const activeToggle = document.getElementById('activeToggle');
    const grid = document.getElementById('productsGrid');
    
    if (!grid) return;
    
    const searchTerm = searchInput ? searchInput.value.toLowerCase() : '';
    const category = categoryFilter ? categoryFilter.value : '';
    const activeOnly = activeToggle ? activeToggle.checked : true;

    const filtered = products.filter(product => {
        const matchesSearch = product.name.toLowerCase().includes(searchTerm);
        const matchesCategory = !category || product.category === category;
        const matchesActive = !activeOnly || product.active;
        return matchesSearch && matchesCategory && matchesActive;
    });

    if (filtered.length === 0) {
        grid.innerHTML = '<p class="empty-state">Nenhum produto encontrado</p>';
        return;
    }

    grid.innerHTML = filtered.map(product => `
        <div class="product-card ${!product.active ? 'inactive' : ''}">
            <h3>${product.name}</h3>
            <p class="product-price">R$ ${(product.priceCents / 100).toFixed(2)}</p>
            <p class="product-category">${product.category}</p>
            ${product.active ? `
                <div class="product-actions">
                    <input type="number" id="qty-${product.id}" value="1" min="1" max="${product.stock}">
                    <button class="btn btn-primary" onclick="addToCart(${product.id})">Adicionar</button>
                </div>
            ` : '<p class="inactive-label">Indisponível</p>'}
        </div>
    `).join('');
}

// Adicionar ao carrinho
function addToCart(productId) {
    const product = products.find(p => p.id === productId);
    const qtyInput = document.getElementById(`qty-${productId}`);
    const quantity = parseInt(qtyInput.value);

    if (quantity <= 0) {
        alert('Quantidade deve ser maior que zero');
        return;
    }

    if (quantity > product.stock) {
        alert(`Estoque insuficiente. Disponível: ${product.stock}`);
        return;
    }

    const existingItem = cart.find(item => item.productId === productId);
    if (existingItem) {
        existingItem.quantity += quantity;
    } else {
        cart.push({
            productId: product.id,
            name: product.name,
            priceCents: product.priceCents,
            quantity
        });
    }

    saveCart();
    renderCart();
    updateCartCount();
    qtyInput.value = 1;
}

// Renderizar carrinho
function renderCart() {
    const container = document.getElementById('cartItems');
    if (cart.length === 0) {
        container.innerHTML = '<p class="empty-cart">Carrinho vazio</p>';
        document.getElementById('cartTotal').textContent = 'R$ 0,00';
        return;
    }

    container.innerHTML = cart.map((item, index) => `
        <div class="cart-item">
            <div>
                <strong>${item.name}</strong>
                <p>R$ ${(item.priceCents / 100).toFixed(2)} x ${item.quantity}</p>
                <strong>Subtotal: R$ ${((item.priceCents * item.quantity) / 100).toFixed(2)}</strong>
            </div>
            <button class="btn-remove" onclick="removeFromCart(${index})">🗑️</button>
        </div>
    `).join('');

    const total = cart.reduce((sum, item) => sum + (item.priceCents * item.quantity), 0);
    document.getElementById('cartTotal').textContent = `R$ ${(total / 100).toFixed(2)}`;
}

// Remover do carrinho
function removeFromCart(index) {
    cart.splice(index, 1);
    saveCart();
    renderCart();
    updateCartCount();
}

// Salvar carrinho no localStorage
function saveCart() {
    localStorage.setItem('cart', JSON.stringify(cart));
}

// Atualizar contador do carrinho
function updateCartCount() {
    const count = cart.reduce((sum, item) => sum + item.quantity, 0);
    document.getElementById('cartCount').textContent = count;
    document.getElementById('fabCount').textContent = count;
}

// Toggle carrinho
function toggleCart() {
    const panel = document.getElementById('cartPanel');
    panel.classList.toggle('active');
}

// Configurar filtros
function setupFilters() {
    document.getElementById('searchInput').addEventListener('input', renderProducts);
    document.getElementById('categoryFilter').addEventListener('change', renderProducts);
    document.getElementById('activeToggle').addEventListener('change', renderProducts);
}

// Checkout
function showCheckoutForm() {
    if (cart.length === 0) {
        alert('Carrinho vazio');
        return;
    }
    
    // Calcular e mostrar total
    const totalCents = cart.reduce((sum, item) => sum + (item.priceCents * item.quantity), 0);
    document.getElementById('checkoutTotal').textContent = `R$ ${(totalCents / 100).toFixed(2)}`;
    
    // Pré-preencher com o valor total
    document.getElementById('paymentAmount').value = (totalCents / 100).toFixed(2);
    
    // Resetar mensagens
    document.getElementById('changeInfo').style.display = 'none';
    document.getElementById('insufficientInfo').style.display = 'none';
    
    document.getElementById('checkoutModal').classList.add('active');
}

function closeCheckoutModal() {
    document.getElementById('checkoutModal').classList.remove('active');
    document.getElementById('orderSuccess').style.display = 'none';
    document.getElementById('checkoutForm').style.display = 'block';
}

document.getElementById('checkoutForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    // Calcular totais
    const totalCents = cart.reduce((sum, item) => sum + (item.priceCents * item.quantity), 0);
    const paymentAmountReais = parseFloat(document.getElementById('paymentAmount').value);
    const paymentAmountCents = Math.round(paymentAmountReais * 100);
    
    // Validar valor
    if (paymentAmountCents < totalCents) {
        alert('Valor de pagamento insuficiente!');
        return;
    }
    
    // Calcular troco
    const changeCents = paymentAmountCents - totalCents;
    
    const orderData = {
        customerId: parseInt(document.getElementById('customerId').value),
        items: cart.map(item => ({
            productId: item.productId,
            quantity: item.quantity
        }))
    };
    
    console.log('Enviando pedido:', JSON.stringify(orderData, null, 2));

    try {
        // Criar pedido
        const order = await API.createOrder(orderData);
        console.log('Pedido criado:', order);
        
        // Processar pagamento com o valor que a pessoa está pagando
        const paymentMethod = document.getElementById('paymentMethod').value;
        const paymentData = {
            orderId: order.id,
            method: paymentMethod,
            amountCents: paymentAmountCents //Valor que a pessoa está pagando
        };
        
        console.log('Enviando pagamento:', JSON.stringify(paymentData, null, 2));
        
        await API.processPayment(paymentData);
        console.log('Pagamento processado com sucesso!');
        
        // Salvar pedido no localStorage
        const orders = JSON.parse(localStorage.getItem('userOrders') || '[]');
        orders.push(order);
        localStorage.setItem('userOrders', JSON.stringify(orders));

        // Mostrar sucesso com troco
        document.getElementById('checkoutForm').style.display = 'none';
        document.getElementById('orderSuccess').style.display = 'block';
        document.getElementById('orderNumber').textContent = order.id;
        
        // Mostrar troco se houver
        if (changeCents > 0) {
            document.getElementById('changeFinal').style.display = 'block';
            document.getElementById('changeFinalAmount').textContent = (changeCents / 100).toFixed(2);
        } else {
            document.getElementById('changeFinal').style.display = 'none';
        }

        // Limpar carrinho
        cart = [];
        saveCart();
        renderCart();
        updateCartCount();
        toggleCart();
        
    } catch (error) {
        console.error('Erro completo:', error);
        alert('Erro ao finalizar pedido: ' + error.message);
    }
});



function viewOrders() {
    window.location.href = 'orders.html';
}
