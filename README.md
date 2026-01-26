# 🛒 Order Management Application

Sistema completo de gerenciamento de pedidos desenvolvido com **Spring Boot** e **Angular**, permitindo a gestão de produtos, pedidos e pagamentos.

## 📋 Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Execução](#instalação-e-execução)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [API Endpoints](#api-endpoints)
- [Banco de Dados](#banco-de-dados)

## 🎯 Sobre o Projeto

Aplicação full-stack para gerenciamento de pedidos que permite:
- Cadastro e listagem de produtos com filtros
- Criação e acompanhamento de pedidos
- Processamento de pagamentos com múltiplos métodos (PIX, Cartão, Boleto)
- Interface responsiva e moderna

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 21**
- **Spring Boot 4.0.1**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Validation
- **SQLite** - Banco de dados
- **Flyway** - Migrations e controle de versão do banco
- **MapStruct 1.6.3** - Mapeamento objeto-relacional
- **SpringDoc OpenAPI** - Documentação da API

### Frontend
- **Angular 20.3.0** - Framework principal
- **TypeScript** - Linguagem de programação
- **Angular SSR** - Server-Side Rendering
- **TailwindCSS 4.1.18** - Estilização
- **MDB Angular UI Kit 9.1.0** - Componentes UI
- **Lucide Angular** - Ícones
- **Font Awesome 7.1.0** - Ícones adicionais
- **RxJS** - Programação reativa

## ✨ Funcionalidades

### Produtos
- ✅ Listagem de produtos com filtros (nome, categoria, status)
- ✅ Cadastro de novos produtos
- ✅ Edição de produtos existentes
- ✅ Exclusão de produtos
- ✅ Ativação/Desativação de produtos

### Pedidos
- ✅ Criação de pedidos com múltiplos itens
- ✅ Listagem de pedidos
- ✅ Visualização detalhada de pedidos
- ✅ Cancelamento de pedidos
- ✅ Cálculo automático de totais

### Clientes
- ✅ Cadastro de clientes
- ✅ Listagem de clientes
- ✅ Vinculação de pedidos aos clientes

### Pagamentos
- ✅ Registro de pagamentos
- ✅ Suporte a múltiplos métodos (PIX, CARD, BOLETO)
- ✅ Histórico de pagamentos por pedido

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

### Backend
```
Controller → Service → Repository → Database
     ↓
   DTO/Mapper
```

- **Controllers**: Endpoints REST da API
- **Services**: Lógica de negócio
- **Repositories**: Acesso aos dados
- **Domain**: Entidades JPA
- **DTOs**: Objetos de transferência de dados
- **Mappers**: Conversão entre entidades e DTOs

### Frontend
```
Components → Services → HTTP Client → Backend API
     ↓
  Models
```

- **Components**: Componentes Angular (layout, orders, products)
- **Services**: Comunicação com a API
- **Models**: Interfaces e classes TypeScript
- **Routes**: Configuração de rotas

## 📦 Pré-requisitos

- **Java 21** ou superior
- **Maven 3.8+**
- **Node.js 18+** e **npm 9+**
- **Angular CLI 20+**

## 🔧 Instalação e Execução

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd order-management-application
```

### 2. Backend

#### Executar com Maven:
```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

#### Ou usar o Maven Wrapper:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

O backend estará disponível em: `http://localhost:8080`

**Documentação da API (Swagger):** `http://localhost:8080/swagger-ui.html`

### 3. Frontend

```bash
# Navegar para o diretório frontend
cd frontend

# Instalar dependências
npm install

# Executar em modo desenvolvimento
npm start
```

O frontend estará disponível em: `http://localhost:4200`

#### Build de produção:
```bash
npm run build
```

#### SSR (Server-Side Rendering):
```bash
npm run serve:ssr:frontend
```

## 📁 Estrutura do Projeto

```
order-management-application/
│
├── src/main/java/com/example/omp/     # Backend Java
│   ├── controller/                     # REST Controllers
│   ├── service/                        # Camada de serviços
│   ├── repository/                     # Repositórios JPA
│   ├── domain/                         # Entidades
│   ├── dto/                            # Data Transfer Objects
│   ├── mapper/                         # MapStruct mappers
│   └── docs/                           # Documentação OpenAPI
│
├── src/main/resources/
│   ├── application.properties          # Configurações do Spring
│   └── db/migration/                   # Scripts Flyway
│
├── frontend/                           # Frontend Angular
│   └── src/
│       └── app/
│           ├── components/             # Componentes UI
│           │   ├── layout/             # Layout (navbar, login, etc)
│           │   ├── orders/             # Pedidos
│           │   └── products/           # Produtos
│           ├── services/               # Serviços HTTP
│           └── models/                 # Modelos TypeScript
│
├── pom.xml                             # Dependências Maven
├── mvnw                                # Maven Wrapper
└── database.db                         # SQLite Database (gerado automaticamente)
```

## 🌐 API Endpoints

### Products
```
GET    /api/products           # Listar produtos (com filtros opcionais)
GET    /api/products/{id}      # Buscar produto por ID
POST   /api/products           # Criar novo produto
DELETE /api/products/{id}      # Deletar produto
```

### Orders
```
GET    /api/orders             # Listar pedidos
GET    /api/orders/{id}        # Buscar pedido por ID
POST   /api/orders             # Criar novo pedido
PUT    /api/orders/{id}        # Cancelar pedido
```

### Customers
```
GET    /api/customers          # Listar clientes
GET    /api/customers/{id}     # Buscar cliente por ID
POST   /api/customers          # Criar novo cliente
```

### Payments
```
GET    /api/payments           # Listar pagamentos
POST   /api/payments           # Registrar pagamento
```

**Nota:** Acesse `http://localhost:8080/swagger-ui.html` para documentação interativa completa da API.

## 🗄️ Banco de Dados

O projeto utiliza **SQLite** com as seguintes tabelas:

### Estrutura
- **customers** - Dados dos clientes
- **products** - Catálogo de produtos
- **orders** - Pedidos realizados
- **order_items** - Itens de cada pedido
- **payments** - Pagamentos dos pedidos

### Migrations
As migrations são gerenciadas pelo **Flyway** e executadas automaticamente na inicialização:
- `V1__create-tables.sql` - Criação das tabelas principais

### Localização do Banco
O arquivo do banco de dados SQLite é criado em:
```
C:/Users/Pedro/IdeaProjects/order-management-application/database.db
```

**Nota:** Para ambientes de produção, considere migrar para PostgreSQL ou MySQL.

## 🎨 Interface do Usuário

A interface foi desenvolvida com:
- Design responsivo com TailwindCSS
- Componentes Material Design (MDB Angular)
- Navegação intuitiva com Angular Router
- Sistema de autenticação (login)
- Páginas de listagem e detalhes para produtos e pedidos

## 🧪 Testes

### Backend
```bash
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## 📝 Configurações

### Backend (`application.properties`)
```properties
spring.application.name="Nome da sua Aplicação"
spring.datasource.url="URL do seu banco"
spring.datasource.driver-class-name=org.sqlite.JDBC
```

### Frontend (API Base URL)
Os serviços apontam para: `http://localhost:8080/api`

Para alterar, edite os arquivos em `frontend/src/app/services/`

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto foi desenvolvido como teste técnico.

## 👤 Autor

Pedro

---

⭐ Se este projeto foi útil para você, considere dar uma estrela!
