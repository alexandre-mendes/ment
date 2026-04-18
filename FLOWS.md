# Documentação de Fluxos - Microserviço de Farmácia

Este documento detalha os fluxos de negócio, APIs e a comunicação assíncrona via filas implementadas neste microserviço.

---

## 1. Visão Geral

O serviço é responsável por gerenciar prescrições médicas e o estoque de medicamentos. Ele recebe solicitações para criar novas prescrições de forma assíncrona e fornece uma API para gerenciar o ciclo de vida dessas prescrições, garantindo a consistência do estoque.

---

## 2. Fluxo de Criação de Prescrição (Assíncrono)

Este fluxo descreve como uma nova prescrição é criada no sistema.

- **Origem:** Um sistema externo (ex: sistema de atendimento médico) publica uma mensagem em uma fila.
- **Fila de Entrada:** `request_medical`

### 2.1. Payload da Mensagem (Exemplo)

A mensagem na fila `request_medical` deve seguir a estrutura JSON abaixo:

```json
{
  "patientId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
  "doctorId": "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
  "sector": "EMERGENCY",
  "medications": [
    {
      "medicationId": "a677502f-cbb5-4620-af1d-3bd33bce0964",
      "quantity": 10,
      "observation": "Tomar 1 a cada 8 horas"
    },
    {
      "medicationId": "b899613g-dcc6-5731-bge2-4ce44cde1075",
      "quantity": 2,
      "observation": "Uso contínuo"
    }
  ],
  "metadata": "{\"priority\":\"high\"}"
}
```

### 2.2. Processamento

1.  O `PrescriptionListener` consome a mensagem da fila `request_medical`.
2.  O listener invoca o método `prescriptionService.create()`.
3.  O serviço cria uma nova `PrescriptionEntity` no banco de dados com o status inicial **`PENDING`**.
4.  Os itens da prescrição (`PrescriptionItemEntity`) são criados e associados a ela.
5.  Nenhuma outra ação (como baixa de estoque) ocorre neste momento.

---

## 3. Fluxo de Atualização de Status da Prescrição (API)

Após a criação, o status da prescrição pode ser modificado através de uma API REST.

- **Endpoint:** `PATCH /v1/prescriptions/{id}`
- **Parâmetro de URL:** `id` (UUID da prescrição)

### 3.1. Payload da Requisição (Exemplo)

```json
{
  "status": "AUTHORIZED"
}
```
*Valores possíveis para `status`: `AUTHORIZED`, `CANCELED`.*

### 3.2. Regras de Negócio e Transições de Estado

O sistema aplica regras estritas para a transição de status:

1.  **Não é permitido alterar para `PENDING`:** Uma vez que a prescrição foi criada, ela só pode progredir para `AUTHORIZED` ou `CANCELED` através desta API.
2.  **Não é permitido cancelar uma prescrição autorizada:** Se o status atual é `AUTHORIZED`, uma tentativa de mudá-lo para `CANCELED` resultará em erro (`400 Bad Request`).
3.  **Não é permitido autorizar uma prescrição cancelada:** Se o status atual é `CANCELED`, uma tentativa de mudá-lo para `AUTHORIZED` resultará em erro (`400 Bad Request`).

### 3.3. Sub-fluxo: Autorização (Status `AUTHORIZED`)

Quando o status é alterado para `AUTHORIZED`, a lógica de baixa de estoque é acionada.

1.  **Lock Pessimista:** O sistema busca os lotes de estoque (`MedicationLotEntity`) para cada medicamento da prescrição usando um **lock de escrita pessimista** (`SELECT ... FOR UPDATE`). Isso impede que outras transações modifiquem esses registros simultaneamente, evitando condições de corrida.
2.  **Verificação de Estoque:** O sistema verifica se a quantidade total disponível nos lotes (somados) é suficiente para atender à quantidade solicitada na prescrição.
    - **Estoque Insuficiente:** Se o estoque não for suficiente, a operação falha, uma `IllegalStateException` é lançada, e a transação inteira sofre rollback. O `GlobalExceptionHandler` captura essa exceção e retorna uma resposta `HTTP 400 Bad Request` com uma mensagem de erro clara.
3.  **Baixa de Estoque (FIFO):** Se houver estoque suficiente, a baixa é realizada seguindo a regra **FIFO (First-In, First-Out)**, baseada na data de validade (`expirationDate`) dos lotes. Os lotes que vencem primeiro são consumidos primeiro.
4.  **Publicação de Evento:** Após a baixa de estoque e a atualização do status da prescrição para `AUTHORIZED`, uma mensagem é publicada na fila **`authorized_request_medical`**. O payload é a própria prescrição autorizada.

### 3.4. Sub-fluxo: Cancelamento (Status `CANCELED`)

1.  **Atualização de Status:** O status da prescrição é alterado para `CANCELED`.
2.  **Publicação de Evento:** Uma mensagem é publicada na fila **`canceled_request_medical`** para notificar outros sistemas sobre o cancelamento. O payload é a própria prescrição cancelada.

---

## 4. Filas (Queues)

- **`request_medical`**
  - **Propósito:** Receber novas solicitações de prescrição de sistemas externos.
  - **Consumidor:** `PrescriptionListener`.

- **`authorized_request_medical`**
  - **Propósito:** Notificar sistemas interessados que uma prescrição foi autorizada e o estoque foi baixado.
  - **Produtor:** `PrescriptionService` (via `PrescriptionEventPublisher`).

- **`canceled_request_medical`**
  - **Propósito:** Notificar sistemas interessados que uma prescrição foi cancelada.
  - **Produtor:** `PrescriptionService` (via `PrescriptionEventPublisher`).

---

## 5. Gerenciamento de Medicamentos e Estoque (API)

Esta seção descreve os fluxos síncronos para gerenciar o catálogo de medicamentos, seus preços e o estoque.

### 5.1. Cadastro de Medicamento

- **Endpoint:** `POST /v1/medications`
- **Propósito:** Cadastrar um novo medicamento no sistema.
- **Payload da Requisição (Exemplo):**
  ```json
  {
    "name": "Dipirona 500mg",
    "category": "ANALGESIC"
  }
  ```
- **Processamento:** Um novo registro `MedicationEntity` é criado no banco de dados.

### 5.2. Consulta de Medicamentos

- **Endpoint:** `GET /v1/medications`
- **Propósito:** Listar os medicamentos cadastrados, com possibilidade de filtro.
- **Parâmetros de Query (Opcionais):**
  - `name` (String): Filtra medicamentos pelo nome (busca parcial, case-insensitive).
  - `category` (String): Filtra medicamentos pela categoria. Valores possíveis: `ANTIBIOTIC`, `ANALGESIC`, `ANTIVIRAL`, `ANTIFUNGAL`, `ANTIPARASITIC`, `OTHER`.
- **Resposta (Exemplo):**
  ```json
  [
    {
      "id": "a677502f-cbb5-4620-af1d-3bd33bce0964",
      "name": "Dipirona 500mg",
      "category": "ANALGESIC",
      "salesPrice": 15.75,
      "quantity": 100
    }
  ]
  ```

### 5.3. Adicionar Preço ao Medicamento

- **Endpoint:** `PATCH /v1/medications/{medicationId}/price`
- **Propósito:** Adicionar um novo registro de preço para um medicamento, mantendo o histórico. O novo preço se torna o preço de venda atual.
- **Parâmetro de URL:** `medicationId` (UUID do medicamento)
- **Payload da Requisição (Exemplo):**
  ```json
  {
    "price": 18.50
  }
  ```
- **Processamento:** Um novo registro `MedicationPriceEntity` é criado e associado ao medicamento.

### 5.4. Adicionar Lote de Estoque

- **Endpoint:** `PATCH /v1/medications/{medicationId}/lot`
- **Propósito:** Adicionar um novo lote de um medicamento ao estoque.
- **Parâmetro de URL:** `medicationId` (UUID do medicamento)
- **Payload da Requisição (Exemplo):**
  ```json
  {
    "quantity": 200,
    "purchasePrice": 10.20,
    "expirationDate": "2028-12-31"
  }
  ```
- **Processamento:** Um novo registro `MedicationLotEntity` é criado e associado ao medicamento, incrementando o estoque total disponível.
