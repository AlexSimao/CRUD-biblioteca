## Documentação da API Biblioteca - Estudo

Este documento detalha a API do sistema de biblioteca, incluindo entidades, serviços, endpoints, DTOs e exemplos de requisição/resposta.

### 1. Visão Geral da Aplicação

A aplicação Biblioteca é um sistema de gerenciamento de informações para uma biblioteca, desenvolvido com o objetivo de facilitar o controle de livros, autores e empréstimos. Trata-se de um projeto de estudo que implementa uma API RESTful utilizando o framework Spring Boot no backend.

#### 1.1 Arquitetura
#### A aplicação segue o padrão de arquitetura MVC (*Model-View-Controller*), que separa as responsabilidades em três camadas distintas:

* **Model:** Representa os dados da aplicação e as regras de negócio. As entidades (Autor, Livro, Emprestimo) fazem parte do Model.
* **View:** Não está presente nesta aplicação, já que ela é uma API RESTful. Em uma aplicação web tradicional, a View seria responsável por apresentar os dados ao usuário (ex: páginas HTML).
* **Controller:** Responsável por receber as requisições dos clientes (através dos endpoints da API), interagir com o Model (chamando os serviços) e retornar as respostas apropriadas.

#### 1.2 Tecnologias
*  **1 - Spring Boot:** Framework Java utilizado para construir a aplicação. O Spring Boot facilita a criação de aplicações Spring, oferecendo recursos como autoconfiguração, injeção de dependências e um servidor web embarcado.

* **2 - JPA Repository (Spring Data JPA):** Simplifica o acesso ao banco de dados.  As interfaces `AutorRepository`, `LivroRepository` e `EmprestimoRepository` estendem `JpaRepository`, fornecendo métodos para realizar operações CRUD (Create, Read, Update, Delete) de forma simples e eficiente.

* **3 - HATEOAS (Spring HATEOAS):** Permite a criação de APIs RESTful que incluem links para outros recursos relacionados nas respostas. Isso facilita a navegação e a descoberta de funcionalidades por parte dos clientes da API.

* **4 - Tratamento de Exceções:** A aplicação possui um tratamento de exceções centralizado, que garante que a API retorne mensagens de erro consistentes e informativas em caso de problemas.  A classe `ResourceExceptionHandler` utiliza as anotações `@ControllerAdvice` e `@ExceptionHandler` para interceptar as exceções lançadas pela aplicação e convertê-las em respostas HTTP apropriadas.

* **5 - API RESTful:** A aplicação expõe seus serviços através de uma API RESTful, que permite que outras aplicações (clientes) interajam com ela utilizando o protocolo HTTP.  Os Controllers são responsáveis por receber as requisições HTTP e retornar as respostas apropriadas.

* **6 - Banco de Dados:** A aplicação utiliza um banco de dados relacional para armazenar os dados.  O tipo específico de banco de dados não está definido no código fornecido, mas pode ser configurado através das propriedades do Spring Boot (ex: `application.properties`).  Opções comuns incluem MySQL, PostgreSQL, H2 (para desenvolvimento) etc.

### 2. Detalhamento das Entidades

```ruby
+----------------+       +----------------+       +---------------------+
|   tb_livros    |       |   tb_autores   |       |   tb_emprestimos    |
+----------------+       +----------------+       +---------------------+
| id (PK)        |       | id (PK)        |       | id (PK)             |
| titulo         |       | nome           |       | livro_id (FK)       |
| autor_id (FK)  |       +----------------+       | usuario             |
| ano_publicacao |                                | data_emprestimo     |
+----------------+                                | data_devolucao      |
                                                  +---------------------+
```
#### 2.1 Autor

*   **id (Long):** Identificador único (chave primária). Gerado automaticamente.
*   **nome (String):** Nome completo do autor.

#### 2.2 Livro

*   **id (Long):** Identificador único (chave primária). Gerado automaticamente.
*   **titulo (String):** Título do livro.
*   **autor (Autor):** Autor do livro (chave estrangeira, Muitos-para-Um).
*   **ano\_publicacao (String):** Ano de publicação.

#### 2.3 Emprestimo

*   **id (Long):** Identificador único (chave primária). Gerado automaticamente.
*   **livro (Livro):** Livro emprestado (chave estrangeira, Muitos-para-Um).
*   **usuario (String):** Nome do usuário.
*   **dataEmprestimo (String):** Data do empréstimo.
*   **dataDevolucao (String):** Data de devolução (pode ser nulo).

### 3. Serviços da Aplicação

#### 3.1 AutorService

*   **getAll():** Lista todos os autores.
*   **getAllPageable(Pageable pageable):** Página de autores.
*   **findByParamName(String nome, Pageable pageable):** Busca autores por nome (paginado).
*   **findById(long id):** Busca autor por ID.
*   **findLivrosByAutor(long id):** Lista livros de um autor.
*   **createAutor(AutorDTO autorDTO):** Cria autor.
*   **updateAutor(long id, AutorDTO autorDTO):** Atualiza autor.
*   **deleteAutor(long id):** Exclui autor.  Lança `ForeignKeyReferenceException` se o autor tiver livros associados.

#### 3.2 LivroService

*   **findAll():** Lista todos os livros.
*   **findAllPageable(Pageable pageable):** Página de livros.
*   **findByParamTitle(String titulo, Pageable pageable):** Busca livros por título (paginado).
*   **findById(long id):** Busca livro por ID.
*   **createLivro(LivroRequestDTO livroRequestDTO):** Cria livro.  Cria o autor se ele não existir.
*   **updateLivro(Long id, LivroRequestDTO livroRequestDTO):** Atualiza livro.
*   **deleteLivro(long id):** Exclui livro. Lança `ForeignKeyReferenceException` se o livro tiver empréstimos associados.

#### 3.3 EmprestimoService

*   **findAll():** Lista todos os empréstimos.
*   **findAllPageable(Pageable pageable):** Página de empréstimos.
*   **findById(Long id):** Busca empréstimo por ID.
*   **emprestimoAtivo():** Lista empréstimos ativos.
*   **novoEmprestimo(EmprestimoRequestDTO emprestimoDTO):** Cria empréstimo.
*   **devolucaoDeEmprestimo(Long id):** Registra devolução.

### 4. Endpoints da API

#### 4.1 AutorController

*   `GET /autor`: Lista autores.
*   `GET /autor/page`: Página de autores.
*   `GET /autor/page/nome`: Busca autores por nome (paginado).
*   `GET /autor/{id}`: Autor por ID.
*   `GET /autor/{id}/livro`: Livros por autor.
*   `POST /autor`: Cria autor.
    *   **Requisição (exemplo JSON):** `{"nome": "Novo Autor"}`
    *   **Resposta (exemplo JSON):** `{"id": 101, "nome": "Novo Autor", "links":[{"rel":"all","href":"http://localhost:8080/autor"}]}`
*   `PUT /autor/{id}`: Atualiza autor.
    *   **Requisição (exemplo JSON):** `{"nome": "Autor Atualizado"}`
    *   **Resposta (exemplo JSON):** `{"id": 101, "nome": "Autor Atualizado", "links":[{"rel":"all","href":"http://localhost:8080/autor"}]}`
*   `DELETE /autor/{id}`: Exclui autor.

#### 4.2 LivroController

*   `GET /livro`: Lista livros.
*   `GET /livro/page`: Página de livros.
*   `GET /livro/page/titulo`: Busca livros por título (paginado).
*   `GET /livro/{id}`: Livro por ID.
*   `POST /livro`: Cria livro.
    *   **Requisição (exemplo JSON):** `{"titulo": "Novo Livro", "autorNome": "Nome do Autor", "ano_publicacao": "2024"}`
    *   **Resposta (exemplo JSON):** `{"id": 201, "titulo": "Novo Livro", "autor": {"id": 101, "nome": "Nome do Autor"}, "ano_publicacao": "2024", "links":[{"rel":"all","href":"http://localhost:8080/livro"}]}`
*   `PUT /livro/{id}`: Atualiza livro.
    *   **Requisição (exemplo JSON):** `{"titulo": "Livro Atualizado", "autorNome": "Nome do Autor", "ano_publicacao": "2025"}`
    *   **Resposta (exemplo JSON):** `{"id": 201, "titulo": "Livro Atualizado", "autor": {"id": 101, "nome": "Nome do Autor"}, "ano_publicacao": "2025", "links":[{"rel":"all","href":"http://localhost:8080/livro"}]}`
*   `DELETE /livro/{id}`: Exclui livro.

#### 4.3 EmprestimoController

*   `GET /emprestimo`: Lista empréstimos.
*   `GET /emprestimo/page`: Página de empréstimos.
*   `GET /emprestimo/{id}`: Empréstimo por ID.
*   `GET /emprestimo/ativos`: Empréstimos ativos.
*   `POST /emprestimo`: Cria empréstimo.
    *   **Requisição (exemplo JSON):** `{"livro_id": 201, "usuario": "Nome do Usuário", "dataEmprestimo": "2025-05-10"}`
    *   **Resposta (exemplo JSON):** `{"id": 301, "livro": {"id": 201, "titulo": "Nome do Livro", "autor": {"id": 101, "nome": "Nome do Autor"}, "ano_publicacao": "2024"}, "usuario": "Nome do Usuário", "dataEmprestimo": "2025-05-10", "dataDevolucao": null, "links":[{"rel":"all","href":"http://localhost:8080/emprestimo"}]}`
*   `PUT /emprestimo/{id}`: Registra devolução.
    *   **Resposta (exemplo JSON):** `{"id": 301, "livro": {"id": 201, "titulo": "Nome do Livro", "autor": {"id": 101, "nome": "Nome do Autor"}, "ano_publicacao": "2024"}, "usuario": "Nome do Usuário", "dataEmprestimo": "2025-05-10", "dataDevolucao": "2025-05-20", "links":[{"rel":"all","href":"http://localhost:8080/emprestimo"}]}`

### 5. Detalhamento dos DTOs

#### 5.1 LivroRequestDTO

*   **id (Long):** ID do livro (opcional em criações).
*   **titulo (String):** Título do livro.
*   **autorNome (String):** Nome do autor.
*   **ano\_publicacao (String):** Ano de publicação.

#### 5.2 EmprestimoRequestDTO

*   **id (Long):** ID do empréstimo (opcional em criações).
*   **livro\_id (Long):** ID do livro.
*   **usuario (String):** Nome do usuário.

#### 5.3 AutorDTO

*   **id (Long):** Identificador único do autor.
*   **nome (String):** Nome do autor.
*   **links (List\<Link>):** Links HATEOAS para outras operações relacionadas ao autor.  Inclui links para:
    *   `self`: O próprio recurso autor.
    *   `all`: A lista de todos os autores.

#### 5.4 LivroDTO

*   **id (Long):** Identificador único do livro.
*   **titulo (String):** Título do livro.
*   **autor (Autor):** Autor do livro.
*   **ano\_publicacao (String):** Ano de publicação.
*   **links (List\<Link>):** Links HATEOAS. Inclui links para:
    *   `self`: O próprio recurso livro.
    *   `all`: A lista de todos os livros.

#### 5.5 EmprestimoDTO

*   **id (Long):** Identificador único do empréstimo.
*   **livro (Livro):** Livro emprestado.
*   **usuario (String):** Nome do usuário.
*   **dataEmprestimo (String):** Data do empréstimo.
*   **dataDevolucao (String):** Data de devolução.
*   **links (List\<Link>):** Links HATEOAS. Inclui links para:
    *   `self`: O próprio recurso empréstimo.
    *   `all`: A lista de todos os empréstimos.

### 6. Tratamento de Exceções

A aplicação possui um tratamento de exceções centralizado em `ResourceExceptionHandler`. As seguintes exceções são tratadas:

*   **EntityNotFoundException:** Retorna status `404 Not Found` com mensagem de erro.
*   **IllegalArgumentException:** Retorna status `400 Bad Request` com mensagem de erro.
*   **ForeignKeyReferenceException:** Retorna status `409 Conflict` com mensagem de erro, indicando que a operação não pode ser realizada devido a uma referência existente em outra entidade (ex: tentar deletar um autor que possui livros).

As respostas de erro seguem o formato:

```json
{
  "timestamp": "Data e hora do erro",
  "status": "Código de status HTTP",
  "error": "Nome do erro",
  "message": "Mensagem detalhada do erro",
  "path": "Caminho da requisição que causou o erro"
}
```

