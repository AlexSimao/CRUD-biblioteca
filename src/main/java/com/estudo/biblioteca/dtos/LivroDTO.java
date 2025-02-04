package com.estudo.biblioteca.dtos;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estudo.biblioteca.controllers.LivroController;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.entities.Livro;

public class LivroDTO extends RepresentationModel<LivroDTO> {

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

    // Adicionando link HATEOAS
    add(linkTo(methodOn(LivroController.class).findLivrosById(id)).withSelfRel());
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

  public Livro toEntity() {
    Livro livro = new Livro();
    BeanUtils.copyProperties(this, livro);
    return livro;
  }

}
