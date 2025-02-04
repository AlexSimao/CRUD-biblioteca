package com.estudo.biblioteca.dtos;

import com.estudo.biblioteca.entities.Emprestimo;

public class EmprestimoRequestDTO {

  private Long id;
  private Long livro_id;
  private String usuario;
  private String dataEmprestimo;
  private String dataDevolucao;

  public EmprestimoRequestDTO() {

  }

  public EmprestimoRequestDTO(Emprestimo entity) {
    this.id = entity.getId();
    this.livro_id = entity.getLivro().getId();
    this.usuario = entity.getUsuario();
    this.dataEmprestimo = entity.getDataEmprestimo();
    this.dataDevolucao = entity.getDataDevolucao();
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

  public String getDataEmprestimo() {
    return dataEmprestimo;
  }

  public void setDataEmprestimo(String data_emprestimo) {
    this.dataEmprestimo = data_emprestimo;
  }

  public String getDataDevolucao() {
    return dataDevolucao;
  }

  public void setDataDevolucao(String data_devolucao) {
    this.dataDevolucao = data_devolucao;
  }

}
