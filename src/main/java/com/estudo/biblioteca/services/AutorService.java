package com.estudo.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.repositories.AutorRepository;

@Service
public class AutorService {

  @Autowired
  private AutorRepository autorRepository;

  @Transactional(readOnly = true)
  public List<AutorDTO> getAll() {
    List<Autor> result = autorRepository.findAll();
    // List<AutorDTO> dto = result.stream().map(autor -> new AutorDTO(autor)).toList();
    List<AutorDTO> dto = result.stream().map(AutorDTO::new).toList();
    return dto;
  }

  @Transactional(readOnly = true)
  public AutorDTO findById(@PathVariable long id) {
    Autor result = autorRepository.findById(id).get();
    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

}
