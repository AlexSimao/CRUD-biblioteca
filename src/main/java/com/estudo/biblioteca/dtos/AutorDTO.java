package com.estudo.biblioteca.dtos;

import org.springframework.beans.BeanUtils;

import com.estudo.biblioteca.entities.Autor;

public class AutorDTO {

  private Long id;
  private String nome;

  public AutorDTO() {

  }

  public AutorDTO(Autor entity) {
    // this.id = entity.getId();
    // this.nome = entity.getNome();
    BeanUtils.copyProperties(entity, this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

}
