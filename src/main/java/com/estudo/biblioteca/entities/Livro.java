package com.estudo.biblioteca.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_livros")
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String titulo;

  @ManyToOne()
  @JoinColumn(name = "autor_id")
  private Autor autorId;
  private String ano_publicacao;

  Livro() {

  }

  public Livro(Long id, String titulo, Autor autorId, String ano_publicacao) {
    this.id = id;
    this.titulo = titulo;
    this.autorId = autorId;
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

  public Autor getAutorId() {
    return autorId;
  }

  public void setAutorId(Autor autorId) {
    this.autorId = autorId;
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
    Livro other = (Livro) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
