# 🚲 Sistema de Gestão de Oficinas de Bicicletas

Repositório do projeto final desenvolvido para a disciplina de **Arquitetura Java**.  
O sistema é uma API REST para gerenciar uma oficina de bicicletas, permitindo o cadastro de **bicicletas**, **clientes**, **mecânicos** e **ordens de serviço (Service Orders)**, com controle de **peças utilizadas** e **valores**.

---

## ✨ Funcionalidades Principais

* **Gestão de Clientes:** CRUD completo para clientes, incluindo busca por nome e CPF.
* **Gestão de Mecânicos:** CRUD completo para mecânicos, com busca por especialidade e status (ativo/inativo).
* **Gestão de Bicicletas:** CRUD completo para bicicletas, com associação ao cliente proprietário.
* **Gestão de Ordens de Serviço (Service Orders):**
  * Registro de entrada e saída de bicicletas.
  * Associação a cliente e mecânico responsáveis.
  * Cálculo de valor total com base nas peças usadas.
  * Cadastro automático de **PartUsed** em cascata.
* **Carga Inicial de Dados:** O sistema é populado com dados iniciais a partir de arquivos `.txt` na inicialização (clientes, mecânicos, bicicletas e ordens de serviço).
* **Documentação Interativa:** A API é totalmente documentada e testável via Swagger UI.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17  
* **Framework:** Spring Boot 3.2.5  
* **Acesso a Dados:** Spring Data JPA com Hibernate  
* **Banco de Dados:** H2 (persistido em arquivo `./data/bikemanagerdb`)  
* **Validações:** Bean Validation  
* **Documentação:** SpringDoc (Swagger UI)  
* **Build:** Maven  

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

* Java 17 (ou superior)
* Apache Maven 3.8 (ou superior)

### Passos

1. **Clone o repositório:**  

   ```bash
   git clone https://github.com/RodrigoPMelo/bike-service-manager.git
   cd bike-service-manager
   ```

2. **Execute a aplicação com Maven:**  

   ```bash
   mvn spring-boot:run
   ```

   A API estará rodando e pronta para receber requisições.

3. **Acesse os principais endpoints:**
   * **API Base URL:** `http://localhost:8080`
   * **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   * **Console do H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
     * **JDBC URL:** `jdbc:h2:file:./data/bikemanagerdb`
     * **User Name:** `sa`
     * **Password:** *(deixe em branco)*

---

## 🗺️ Estrutura da API (Endpoints)

### Client Controller (`/clients`)

* `GET /clients` — Lista todos os clientes.
* `GET /clients/{id}` — Busca um cliente por ID.
* `GET /clients/by-cpf/{cpf}` — Busca um cliente por CPF.
* `GET /clients/{id}/bikes` — Lista bicicletas de um cliente específico.
* `POST /clients` — Cria um novo cliente.
* `PUT /clients/{id}` — Atualiza um cliente existente.
* `DELETE /clients/{id}` — Exclui um cliente.

---

### Mechanic Controller (`/mechanics`)

* `GET /mechanics` — Lista todos os mecânicos.
* `GET /mechanics/{id}` — Busca um mecânico por ID.
* `GET /mechanics/active` — Filtra mecânicos ativos.
* `POST /mechanics` — Cria um novo mecânico.
* `PUT /mechanics/{id}` — Atualiza um mecânico existente.
* `PATCH /mechanics/{id}/inactivate` — Inativa um mecânico.
* `DELETE /mechanics/{id}` — Exclui um mecânico.

---

### Bike Controller (`/bikes`)

* `GET /bikes` — Lista todas as bicicletas.
* `GET /bikes/{id}` — Busca uma bicicleta por ID.
* `POST /bikes` — Cria uma nova bicicleta.
* `PUT /bikes/{id}` — Atualiza uma bicicleta existente.
* `DELETE /bikes/{id}` — Exclui uma bicicleta.

---

### 🧾 Service Order Controller (`/service-orders`)

* `GET /service-orders` — Lista todas as ordens de serviço.
* `GET /service-orders/{id}` — Busca uma ordem de serviço por ID.
* `GET /service-orders/by-client-name?name={name}` — Busca ordens de serviço pelo nome do cliente.
* `GET /service-orders/by-mechanic-specialty?specialty={specialty}` — Busca ordens de serviço pela especialidade do mecânico.
* `POST /service-orders?clientId={clientId}&mechanicId={mechanicId}` — Cria uma nova ordem de serviço com cliente e mecânico associados.
  * **Exemplo de corpo:**

    ```json
    {
      "entryDate": "2025-11-08T08:00:00",
      "estimatedExitDate": "2025-11-09T17:00:00",
      "problemDescription": "Troca de freios e ajuste de marchas",
      "totalValue": 280.0,
      "partsUsed": [
        {
          "description": "Cabo de Freio Shimano",
          "unitValue": 80.0,
          "quantity": 2
        },
        {
          "description": "Pastilha de Freio",
          "unitValue": 60.0,
          "quantity": 2
        }
      ]
    }
    ```

* `PUT /service-orders/{id}` — Atualiza uma ordem de serviço existente.
* `DELETE /service-orders/{id}` — Exclui uma ordem de serviço.

---

## 🧩 Relacionamentos Principais

| Entidade | Relacionamento | Descrição |
|-----------|----------------|-----------|
| **Client** | 1:N com `Bike` | Um cliente pode ter várias bicicletas. |
| **Client** | 1:N com `ServiceOrder` | Um cliente pode ter várias ordens de serviço. |
| **Mechanic** | 1:N com `ServiceOrder` | Um mecânico pode estar vinculado a várias ordens. |
| **ServiceOrder** | N:1 com `Client` e `Mechanic` | Cada OS pertence a um cliente e um mecânico. |
| **ServiceOrder** | 1:N com `PartUsed` | Cada OS possui uma lista de peças usadas. |
| **PartUsed** | N:1 com `ServiceOrder` | Cada peça pertence a uma ordem específica. |

---

## 🧰 Estrutura de Pacotes

``` 
src/main/java/br/edu/infnet/rodrigomeloapi
├── api
│   ├── controller
│   ├── dto
│   ├── error
│   └── loader
├── domain
│   ├── model
│   └── repository
└── infrastructure
    ├── repository
    │   └── jpa
    └── config
```

---

## 📄 Licença

Projeto acadêmico desenvolvido para fins educacionais.  
Distribuição livre para estudo e aprendizado.
