# 🚲 Sistema de Gestão de Oficinas de Bicicletas

Repositório do projeto final desenvolvido para a disciplina de Arquitetura Java. O sistema é uma API REST para gerenciar uma oficina de bicicletas, permitindo o cadastro de bicicletas, clientes e mecânicos.

---

## ✨ Funcionalidades Principais

* **Gestão de Clientes:** CRUD completo para clientes, incluindo busca por nome e CPF.
* **Gestão de Mecânicos:** CRUD completo para mecânicos, com busca por especialidade e status (ativo/inativo).
* **Gestão de Bicicletas:** CRUD completo para bicicletas, com possibilidade de associação a um cliente proprietário.
* **Carga Inicial de Dados:** O sistema é populado com dados iniciais a partir de arquivos `.txt` na inicialização.
* **Documentação Interativa:** A API é totalmente documentada e testável via Swagger UI.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.2.5
* **Acesso a Dados:** Spring Data JPA com Hibernate
* **Banco de Dados:** H2 (em memória, persistido em arquivo)
* **Validações:** Bean Validation
* **Documentação:** SpringDoc (Swagger UI)
* **Build:** Maven

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

* Java 17 (ou superior)
* Apache Maven 3.8 (ou superior)

### Passos

1.  **Clone o repositório:**  
    ```bash
    git clone https://github.com/RodrigoPMelo/bike-service-manager.git  
    cd bike-service-manager
    ```

2.  **Execute a aplicação com Maven:**  

    ```bash
    mvn spring-boot:run
    ```
    A API estará rodando e pronta para receber requisições.

3.  **Acesse os principais endpoints:**
    * **API Base URL:** `http://localhost:8080`
    * **Swagger UI (Documentação Interativa):** `http://localhost:8080/swagger-ui.html`
    * **Console do Banco H2:** `http://localhost:8080/h2-console`
        * **JDBC URL (para login):** `jdbc:h2:file:./data/bikemanagerdb`
        * **User Name:** `sa`
        * **Password:** (deixe em branco)

---

## 🗺️ Estrutura da API (Endpoints)

### Client Controller (`/clients`)
* `GET /clients`: Lista todos os clientes.
* `GET /clients/{id}`: Busca um cliente por ID.
* `GET /clients/by-cpf/{cpf}`: Busca um cliente por CPF.
* `GET /clients/{id}/bikes`: Lista as bicicletas de um cliente específico.
* `POST /clients`: Cria um novo cliente.
* `PUT /clients/{id}`: Atualiza um cliente existente.
* `DELETE /clients/{id}`: Exclui um cliente.

### Mechanic Controller (`/mechanics`)
* `GET /mechanics`: Lista todos os mecânicos.
* `GET /mechanics/{id}`: Busca um mecânico por ID.
* `GET /mechanics/active`: Filtra mecânicos por status (ativo/inativo).
* `POST /mechanics`: Cria um novo mecânico.
* `PUT /mechanics/{id}`: Atualiza um mecânico existente.
* `PATCH /mechanics/{id}/inactivate`: Inativa um mecânico.
* `DELETE /mechanics/{id}`: Exclui um mecânico.

### Bike Controller (`/bikes`)
* `GET /bikes`: Lista todas as bicicletas.
* `GET /bikes/{id}`: Busca uma bicicleta por ID.
* `POST /bikes`: Cria uma nova bicicleta.
* `PUT /bikes/{id}`: Atualiza uma bicicleta existente.
* `DELETE /bikes/{id}`: Exclui uma bicicleta.