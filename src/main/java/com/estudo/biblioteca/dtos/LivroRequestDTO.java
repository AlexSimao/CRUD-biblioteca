package com.estudo.biblioteca.dtos;

public class LivroRequestDTO {

  private Long id;
  private String titulo;
  private String autorNome;
  private String ano_publicacao;

  public LivroRequestDTO() {

  }

  public LivroRequestDTO(Long id, String titulo, String autorNome, String ano_publicacao) {
    this.id = id;
    this.titulo = titulo;
    this.autorNome = autorNome;
    this.ano_publicacao = ano_publicacao;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getAutorNome() {
    return autorNome;
  }

  public void setAutor(String autor) {
    this.autorNome = autor;
  }

  public String getAno_publicacao() {
    return ano_publicacao;
  }

  public void setAno_publicacao(String ano_publicacao) {
    this.ano_publicacao = ano_publicacao;
  }

}
