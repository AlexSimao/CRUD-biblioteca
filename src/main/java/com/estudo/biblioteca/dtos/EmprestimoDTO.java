package com.estudo.biblioteca.dtos;

import com.estudo.biblioteca.entities.Emprestimo;

public class EmprestimoDTO {

  private Long id;
  private Long livro_id;
  private String usuario;
  private String data_emprestimo;
  private String data_devolucao;

  public EmprestimoDTO() {

  }

  public EmprestimoDTO(Emprestimo entity) {
    this.id = entity.getId();
    this.livro_id = entity.getLivro().getId();
    this.usuario = entity.getUsuario();
    this.data_emprestimo = entity.getData_emprestimo();
    this.data_devolucao = entity.getData_devolucao();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getLivro_id() {
    return livro_id;
  }

  public void setLivro_id(Long livro_id) {
    this.livro_id = livro_id;
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

}
