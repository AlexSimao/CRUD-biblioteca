
---

# Sistema de Gerenciamento de Biblioteca

## Estrutura do Banco de Dados

O banco de dados consiste em três tabelas principais: `Livros`, `Autores` e `Empréstimos`.

### Diagramas das Tabelas

```
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

### Descrição das Tabelas

#### Livros

- `id`: Identificador único para cada livro (chave primária).
- `titulo`: Título do livro.
- `autor_id`: Identificador do autor do livro (chave estrangeira referenciando `Autores`).
- `ano_publicacao`: Ano de publicação do livro.

#### Autores

- `id`: Identificador único para cada autor (chave primária).
- `nome`: Nome do autor.

#### Empréstimos

- `id`: Identificador único para cada empréstimo (chave primária).
- `livro_id`: Identificador do livro emprestado (chave estrangeira referenciando `Livros`).
- `usuario`: Nome do usuário que fez o empréstimo.
- `data_emprestimo`: Data em que o livro foi emprestado.
- `data_devolucao`: Data em que o livro foi devolvido.


## Regras de Negócio

### Adicionar um Livro

- Quando um novo livro é adicionado à biblioteca, ele deve ter um título, um autor válido e um ano de publicação.

### Registrar um Empréstimo

- Quando um livro é emprestado, deve ser registrada a data de empréstimo e o nome do usuário.
- A data de devolução deve ser registrada quando o livro for devolvido.

### Relatório de Empréstimos Ativos

- Gerar um relatório de todos os livros que ainda não foram devolvidos (ou seja, onde a data de devolução é nula).

---
