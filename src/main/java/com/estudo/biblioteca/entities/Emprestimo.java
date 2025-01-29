package com.estudo.biblioteca.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_emprestimos")
public class Emprestimo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "livro_id")
  private Livro livro;

  private String usuario;
  private String data_emprestimo;
  private String data_devolucao;

  public Emprestimo() {

  }

  public Emprestimo(Long id, Livro livro, String usuario, String data_emprestimo, String data_devolucao) {
    this.id = id;
    this.livro = livro;
    this.usuario = usuario;
    this.data_emprestimo = data_emprestimo;
    this.data_devolucao = data_devolucao;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Livro getLivro() {
    return livro;
  }

  public void setLivro(Livro livro) {
    this.livro = livro;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getData_emprestimo() {
    return data_emprestimo;
  }

  public void setData_emprestimo(String data_emprestimo) {
    this.data_emprestimo = data_emprestimo;
  }

  public String getData_devolucao() {
    return data_devolucao;
  }

  public void setData_devolucao(String data_devolucao) {
    this.data_devolucao = data_devolucao;
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
    Emprestimo other = (Emprestimo) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
