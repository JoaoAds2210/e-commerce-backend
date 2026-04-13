# 🛒 E-commerce API

API REST desenvolvida em Java com Spring Boot para gerenciamento de e-commerce de supermercado, permitindo o cadastro de usuários, produtos, pedidos, pagamentos e endereços de entrega.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como Executar](#-como-executar)
- [Endpoints](#-endpoints)
- [Melhorias Futuras](#-melhorias-futuras)

---

## 📖 Sobre o Projeto

O sistema permite que um supermercado gerencie seu catálogo de produtos e o fluxo completo de compras dos clientes de forma organizada.

Funcionalidades principais:

- Cadastro de usuários com validação de CPF, e-mail e senha
- Gerenciamento de produtos por categoria com controle de estoque
- Criação de pedidos com múltiplos itens e cálculo de total automático
- Controle de status dos pedidos (PENDING → PROCESSING → SHIPPED → DELIVERED)
- Registro de pagamentos com suporte a múltiplos métodos
- Gerenciamento de endereços de entrega por usuário
- Mensageria assíncrona com Apache Kafka
- Documentação completa via Swagger UI

---

## 🛠 Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.5 | Framework principal |
| Spring Data JPA | — | Persistência de dados |
| Spring Validation | — | Validação de DTOs |
| MySQL | 8+ | Banco de dados |
| Apache Kafka | — | Mensageria assíncrona |
| Lombok | — | Redução de boilerplate |
| SpringDoc OpenAPI | 3.0.2 | Documentação Swagger |
| Maven | — | Gerenciador de dependências |

---

## 📁 Estrutura do Projeto

src/main/java/com/example/springboot
├── address       → Endereços de entrega dos usuários
├── config        → Configurações gerais (Security, Swagger)
├── order         → Pedidos e status
├── orderItem     → Itens individuais de cada pedido
├── payment       → Pagamentos e métodos de pagamento
├── product       → Produtos e categorias
└── user          → Usuários e roles

---

## 🔮 Melhorias Futuras

- [ ] Autenticação e autorização com Spring Security + JWT
- [ ] Integração com gateway de pagamento (Mercado Pago)
- [ ] Upload de imagens para produtos
- [ ] Paginação e filtros avançados nos endpoints de listagem
- [ ] Testes unitários e de integração
- [ ] Deploy com Docker + Docker Compose
