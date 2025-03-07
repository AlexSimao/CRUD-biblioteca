-- Script SQL para crir e semear o banco de dados para teste.

create table tb_autores (id bigint generated by default as identity, nome varchar(255), primary key (id));
create table tb_emprestimos (id bigint generated by default as identity, livro_id bigint, data_devolucao varchar(255), data_emprestimo varchar(255), usuario varchar(255), primary key (id));
create table tb_livros (autor_id bigint, id bigint generated by default as identity, ano_publicacao varchar(255), titulo varchar(255), primary key (id));
alter table if exists tb_emprestimos add constraint FKfvak9olhkaucsp1y5uwxmiiwe foreign key (livro_id) references tb_livros;
alter table if exists tb_livros add constraint FK45mpyjglx2a6jh4vyx1vf0gd1 foreign key (autor_id) references tb_autores;
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
