package com.estudo.biblioteca.dtos;

import org.springframework.beans.BeanUtils;

import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.entities.Livro;

public class LivroDTO {

  private Long id;
  private String titulo;
  private Autor autor;
  private String ano_publicacao;

  public LivroDTO() {

  }

  public LivroDTO(Livro entity) {
    this.id = entity.getId();
    this.titulo = entity.getTitulo();
    this.autor = entity.getAutor();
    this.ano_publicacao = entity.getAno_publicacao();
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

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
  }

  public String getAno_publicacao() {
    return ano_publicacao;
  }

  public void setAno_publicacao(String ano_publicacao) {
    this.ano_publicacao = ano_publicacao;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    LivroDTO other = (LivroDTO) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public Livro toEntity() {
    Livro livro = new Livro();
    BeanUtils.copyProperties(this, livro);
    return livro;
  }

}
