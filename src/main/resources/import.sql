INSERT INTO tb_autores (nome) VALUES ('Machado de Assis');
INSERT INTO tb_autores (nome) VALUES ('Clarice Lispector');
INSERT INTO tb_autores (nome) VALUES ('Jorge Amado');

INSERT INTO tb_livros (titulo, autor_id, ano_publicacao) VALUES ('Dom Casmurro', 1, 1899);
INSERT INTO tb_livros (titulo, autor_id, ano_publicacao) VALUES ('Memórias Póstumas de Brás Cubas', 1, 1881);
INSERT INTO tb_livros (titulo, autor_id, ano_publicacao) VALUES ('A Hora da Estrela', 2, 1977);
INSERT INTO tb_livros (titulo, autor_id, ano_publicacao) VALUES ('Gabriela, Cravo e Canela', 3, 1958);

INSERT INTO tb_emprestimos (livro_id, usuario, data_emprestimo, data_devolucao) VALUES (2, 'João Pereira', '10-01-2025', NULL);
INSERT INTO tb_emprestimos (livro_id, usuario, data_emprestimo, data_devolucao) VALUES (1, 'Ana Silva', '15-01-2025', '22-01-2025');
INSERT INTO tb_emprestimos (livro_id, usuario, data_emprestimo, data_devolucao) VALUES (4, 'Carlos Souza', '18-01-2025', NULL);
INSERT INTO tb_emprestimos (livro_id, usuario, data_emprestimo, data_devolucao) VALUES (3, 'Maria Oliveira', '20-01-2025', NULL);