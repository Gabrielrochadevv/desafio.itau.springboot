# Desafio de Transações - Java Spring Boot

Este projeto é uma implementação backend simples utilizando Java com Spring Boot para processar transações e gerar estatísticas em tempo real. As transações são armazenadas temporariamente na memória e as estatísticas são atualizadas com base nas transações dos últimos 60 segundos.

---

## 🔧 Tecnologias Utilizadas

- Java 17+
- Spring Boot
  - Spring Web
  - Spring Validation
  - Spring DevTools

---

## 📁 Estrutura do Projeto
<img width="626" height="879" alt="Capturar" src="https://github.com/user-attachments/assets/9086df02-b1e8-47db-9695-5e6b7cfb9158" />

---

## Funcionalidades

- **POST /transacao**  
  Registra uma nova transação se estiver dentro das regras de negócio.  
  Retorna:
  - `201 Created` – transação aceita
  - `422 Unprocessable Entity` – transação inválida (ex: valor negativo)
  - `400 Bad Request` – erro de parsing (ex: JSON mal formatado)

- **GET /transacao**  
  Retorna as estatísticas das transações dos últimos 60 segundos.

- **DELETE /transacao**  
  Remove todas as transações da memória.

---

## 🧠 Regras de Negócio

- Nenhuma persistência em banco de dados — os dados são armazenados em memória (`ConcurrentLinkedQueue`).
- Transações mais antigas que 60 segundos são ignoradas nas estatísticas.
- Validações realizadas com `javax.validation` no DTO:
  - `@NotNull`
  - `@Min(0)`

---

### Model

A classe `Transaction` representa a entidade principal. Como não foi utilizado o Lombok, foram criados apenas os **setters**, pois os atributos são passados via construtor.

---

### DTO

- `TransactionRequest`: contém validações como `@NotNull` e `@Min(0)`.
- `StatisticsResponse`: recebe um objeto `DoubleSummaryStatistics` que calcula:
  - `count`
  - `sum`
  - `avg`
  - `max`
  - `min`

### Service

Classe `TransactionService`:
- Anotada com `@Service`
- Armazena transações na `Queue<ConcurrentLinkedQueue<Transaction>>`
- Métodos:
  - `addTransaction(...)`
  - `clearTransactions()`
  - `getStatistics()` – usa `stream().filter()` para manter apenas transações dos últimos 60 segundos, e aplica `summaryStatistics()`.

### Controller

Classe `TransactionController`:
- Anotada com `@RestController`
- Mapeada em `/transacao`
- Métodos:
  - `POST /` – recebe o DTO com `@Valid`, valida a data e registra a transação.
  - `GET /` – retorna estatísticas atuais.
  - `DELETE /` – limpa todas as transações.

---

## Exemplos de Requisições
### Transação válida

    POST /transacao
{
    "valor": 125.45,
    "dataHora": "2025-07-31T12:33:56.789-03:00"
}
- `201 Created`
  
  ### Transação inválida

    POST /transacao
    {
    "valor": -80.45,
    "dataHora": "2025-07-31T12:33:56.789-03:00"
}
  - `422 Unprocessable Entity`

 ### JSON inválido
  POST /transacao
{
    "valor": -80.45,
    "dataHora": "teste"
}
  - `400 Bad Request`
  
---

### Observações 
- O projeto não utiliza banco de dados.
- O armazenamento é feito apenas em memória.
- Não foi utilizado Lombok de propósito, para demonstrar a criação manual dos métodos.




