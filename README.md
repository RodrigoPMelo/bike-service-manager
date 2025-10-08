# 🚲 Sistema de Gestão de Oficinas de Bicicletas

Repositório do projeto desenvolvido para a disciplina de **Arquitetura Java** (Pós-graduação).  
O sistema tem como objetivo gerenciar uma **oficina de bicicletas**, permitindo o cadastro de bicicletas, clientes, mecânicos e ordens de serviço, com validações, persistência em banco de dados e API REST construída com Spring Boot.

---

## 🧭 Visão Geral

**Tecnologias principais:**

- Java 17+
- Spring Boot (Web, Data JPA, Validation)
- Lombok
- Banco de Dados H2
- Maven

**Arquitetura:** em camadas (Controller → Service → Repository → Model + Loader)

**Banco de Dados:** H2 (em memória)

---

## 🧱 Estrutura do Projeto
```
src/  
└── main/  
       └── java/com/oficinabike/  
         ├── model/        # Entidades do domínio  
         ├── controller/   # Endpoints REST  
         ├── service/      # Regras de negócio  
         ├── repository/   # Interfaces JPA  
         ├── loader/       # Carga inicial de dados  
         └── exception/    # Tratamento de erros e validações  
```
---

## 🗓️ Planejamento de Desenvolvimento

### **📍 Semana 1 — Feature 1: Configuração e Entidade Primária**

**Objetivo:** configurar o projeto e implementar a entidade base `Bicicleta`.

**Tarefas:**

- Criar projeto Spring Boot com dependência *Spring Web*.
- Implementar entidade `Bicicleta` (`id`, `modelo`, `marca`, `ano`, `numeroSerie`, `tipo`).
- Criar `CrudService<T, ID>` e `BicicletaService` com `ConcurrentHashMap`.
- Implementar `BicicletaLoader` para ler `bicicletas.txt`.
- Criar `BicicletaController` expondo `/bicicletas`.
- Testar endpoints via Postman.

**Entrega:** Projeto funcional em memória.  
**Commit:** `feature1-in-memory-setup`

---

### **🧩 Semana 2 — Feature 2: Expansão do Domínio e CRUD Completo**

**Objetivo:** adicionar herança, associação e CRUDs completos.

**Tarefas:**

- Criar classe abstrata `Pessoa` (nome, cpf, email, telefone).
- Subclasses:
  - `Cliente`: fidelidade, endereco, dataCadastro.
  - `Mecanico`: matricula, especialidade, salario, ehAtivo.
- Classe associada: `Endereco` (OneToOne com Cliente).
- Criar Services e Loaders (`ClienteService`, `MecanicoService`).
- Implementar Controllers `/clientes` e `/mecanicos`.
- Criar exceções customizadas (`ClienteNaoEncontradoException`, `MecanicoInvalidoException`).

**Entrega:** CRUD completo em memória com herança e associação.  
**Commit:** `feature2-domain-expansion`

---

### **🗄️ Semana 3 — Feature 3: Persistência Real e API Refinada**

**Objetivo:** substituir armazenamento em memória por banco relacional (H2 + JPA).

**Tarefas:**

- Adicionar dependências: `spring-boot-starter-data-jpa`, `h2`, `lombok`.
- Configurar `application.properties`:

  ```properties
  spring.datasource.url=jdbc:h2:mem:oficinadb
  spring.jpa.hibernate.ddl-auto=update
  spring.h2.console.enabled=true
  ```

- Anotar entidades com `@Entity`.
- Criar repositórios (`BicicletaRepository`, `ClienteRepository`, etc.).
- Atualizar Services para usar `JpaRepository`.
- Refatorar Controllers para usar `ResponseEntity`.
- Implementar status HTTP adequados (`200`, `201`, `204`, `404`, `400`, `409`).

**Entrega:** API persistente com comunicação semântica via HTTP.  
**Commit:** `feature3-persistence-jpa`

---

### **🧮 Semana 4 — Feature 4: Validação, Exceções Globais e Relacionamentos Complexos**

**Objetivo:** implementar validações avançadas, tratamento global e novo relacionamento OneToMany.

**Tarefas:**

- Criar entidades:
  - `OrdemServico`: id, dataEntrada, dataSaida, descricaoProblema, valorTotal, cliente, mecanico.
  - `PecaUtilizada`: id, descricao, valor, quantidade, ordemServico.
- Mapear `OneToMany` e `ManyToOne` entre Ordem e Peça.
- Adicionar Bean Validations:
  - `@NotBlank`, `@Min`, `@Email`, `@Future`, `@Pattern`.
- Criar `GlobalExceptionHandler` com `@ControllerAdvice`.
- Implementar Query Methods:
  - `findByClienteNomeContainingIgnoreCase`
  - `findByMecanicoEspecialidadeStartingWithIgnoreCase`
- Criar `Loader` de ordens (`ordens.txt`).

**Entrega:** API validada, com tratamento global e relacionamento complexo.  
**Commit:** `feature4-validation-relationship`

---

## 🧾 Entrega Final

**Arquivos obrigatórios:**

- Repositório público no GitHub (`oficina-bikes-api`)
- PDF de documentação com:
  - Dados da instituição, curso, disciplina e aluno.
  - Resumo do projeto e instruções de execução.
  - Link do repositório.
  - Prints de endpoints funcionando.

**README.md:**  

- Descrição do sistema  
- Tecnologias usadas  
- Passos para execução  
- Estrutura de entidades e endpoints principais  

---

## 💡 Observações

- Faça commits semanais organizados (`featureX-...`)  
- Utilize o Postman para validar cada etapa.  
- O projeto deve iniciar sem erros e ser acessível via navegador ou `http://localhost:8080/h2-console`.

---

**Autor:** Rodrigo Pompermayer de Melo  
**Curso:** Pós-graduação em Arquitetura de Software com Java  
**Disciplina:** Desenvolvimento Java com Spring Boot  
**Professor:** *Eliberth Moraes*
