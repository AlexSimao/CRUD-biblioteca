package com.estudo.biblioteca.dtos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;

import com.estudo.biblioteca.controllers.EmprestimoController;
import com.estudo.biblioteca.entities.Emprestimo;
import com.estudo.biblioteca.entities.Livro;

public class EmprestimoDTO extends RepresentationModel<EmprestimoDTO> {

  private Long id;
  private Livro livro;
  private String usuario;
  private String dataEmprestimo;
  private String dataDevolucao;

  public EmprestimoDTO() {

  }

  public EmprestimoDTO(Emprestimo entity) {
    this.id = entity.getId();
    this.livro = entity.getLivro();
    this.usuario = entity.getUsuario();
    this.dataEmprestimo = entity.getDataEmprestimo();
    this.dataDevolucao = entity.getDataDevolucao();

    // Adicionando link HATEOAS
    add(linkTo(methodOn(EmprestimoController.class).findById(id)).withSelfRel());
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
