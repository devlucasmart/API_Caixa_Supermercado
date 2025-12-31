## 🛒 Caixa de Supermercado – Backend

Backend da aplicação **Caixa de Supermercado**, desenvolvido em **Spring Boot**, responsável por gerenciar compras e produtos de um sistema de checkout de supermercado.

A aplicação expõe uma **API REST** que retorna os dados em formato **JSON**.


## 🚀 Funcionalidades

- API REST para:
    - Gerenciamento de **produtos**:
        - Cadastro de produtos
        - Consulta de produtos
        - Validação de dados de produto
    - Gerenciamento de **compras**:
        - Registro de compras
        - Consulta de compras
        - Validação de dados de compra
        - 
- Tratamento centralizado de exceções:
    - Produto não encontrado
    - Produto inválido
    - Compra não encontrada
    - Compra inválida

- Retorno de dados em formato **JSON**, seguindo boas práticas de APIs REST.


## Tecnologias Utilizadas

- Java 21+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Hibernate


## 🔗 Endpoints

🛒 Produtos

URL base: /api/mercado/produtos

| Método   | Endpoint                         | Descrição                          | Exemplo                              |
| -------- | -------------------------------- | ---------------------------------- | ------------------------------------ |
| `GET`    | `/api/mercado/produtos`          | Lista todos os produtos            | `/api/mercado/produtos`              |
| `GET`    | `/api/mercado/produtos/{id}`     | Busca produto pelo **ID**          | `/api/mercado/produtos/1`            |
| `POST`   | `/api/mercado/produtos`          | Cadastra um novo produto           | `/api/mercado/produtos`              |
| `PUT`    | `/api/mercado/produtos/{id}`     | Atualiza um produto existente      | `/api/mercado/produtos/1`            |
| `DELETE` | `/api/mercado/produtos/{id}`     | Remove um produto pelo **ID**      | `/api/mercado/produtos/1`            |

🧾 Compras

URL base: /api/mercado/compras

| Método   | Endpoint                                              | Descrição                                     | Exemplo                                           |
| -------- | ----------------------------------------------------- | --------------------------------------------- | ------------------------------------------------- |
| `GET`    | `/api/mercado/compras`                                | Lista todas as compras                        | `/api/mercado/compras`                            |
| `GET`    | `/api/mercado/compras/{id}`                           | Busca compra pelo **ID**                      | `/api/mercado/compras/10`                         |
| `POST`   | `/api/mercado/compras`                                | Registra uma nova compra                      | `/api/mercado/compras`                            |
| `PUT`    | `/api/mercado/compras/{id}`                           | Atualiza uma compra existente                 | `/api/mercado/compras/10`                         |
| `DELETE` | `/api/mercado/compras/{id}`                           | Remove uma compra pelo **ID**                 | `/api/mercado/compras/10`                         |


## Exemplo de saída

```json
[
    {
        "id": 19,
        "dataCompra": "2025-10-21T20:52:42.859195",
        "produtosCompra": [
            {
                "id": 2,
                "nome": "Feijão",
                "preco": 5.79,
                "unidade": "UN"
            },
            {
                "id": 3,
                "nome": "Macarrão",
                "preco": 2.99,
                "unidade": "UN"
            },
            {
                "id": 4,
                "nome": "Refrigerante",
                "preco": 9.79,
                "unidade": "UN"
            }
        ],
        "valorCompra": 18.57,
        "formaPagamento": "DEBITO"
    }
]
