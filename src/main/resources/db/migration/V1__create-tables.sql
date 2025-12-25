CREATE TABLE customers (
                           id INTEGER PRIMARY KEY,
                           name VARCHAR(120) NOT NULL,
                           email VARCHAR(160) UNIQUE NOT NULL,
                           created_at TIMESTAMP NOT NULL
);
CREATE TABLE products (
                          id INTEGER PRIMARY KEY,
                          name VARCHAR(120) NOT NULL,
                          category VARCHAR(60) NOT NULL,
                          price_cents INTEGER NOT NULL CHECK (price_cents >= 0),
                          active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE orders (
                        id INTEGER PRIMARY KEY,
                        customer_id INTEGER NOT NULL,
                        status VARCHAR(20) NOT NULL, -- e.g., 'NEW', 'PAID', 'CANCELLED'
                        created_at TIMESTAMP NOT NULL,
                        FOREIGN KEY (customer_id) REFERENCES customers(id)
);
CREATE TABLE order_items (
                             id INTEGER PRIMARY KEY,
                             order_id INTEGER NOT NULL,
                             product_id INTEGER NOT NULL,
                             quantity INTEGER NOT NULL CHECK (quantity > 0),
                             unit_price_cents INTEGER NOT NULL CHECK (unit_price_cents >= 0),
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (product_id) REFERENCES products(id)
);
CREATE TABLE payments (
                          id INTEGER PRIMARY KEY,
                          order_id INTEGER NOT NULL,
                          method VARCHAR(20) NOT NULL, -- e.g., 'PIX', 'CARD', 'BOLETO'
                          amount_cents INTEGER NOT NULL CHECK (amount_cents >= 0),
                          paid_at TIMESTAMP,
                          FOREIGN KEY (order_id) REFERENCES orders(id)
);