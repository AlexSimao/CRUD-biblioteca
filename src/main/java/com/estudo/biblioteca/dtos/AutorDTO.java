package com.estudo.biblioteca.dtos;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estudo.biblioteca.controllers.AutorController;
import com.estudo.biblioteca.entities.Autor;

public class AutorDTO extends RepresentationModel<AutorDTO> {

  private Long id;
  private String nome;

  public AutorDTO() {

  }

  public AutorDTO(Autor entity) {
    // this.id = entity.getId();
    // this.nome = entity.getNome();
    BeanUtils.copyProperties(entity, this);
    
    // Adicionando link HATEOAS
    add(linkTo(methodOn(AutorController.class).findById(id)).withSelfRel());
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

  public Autor toEntity() {
    Autor autor = new Autor();
    BeanUtils.copyProperties(this, autor);
    return autor;
  }

}
