package com.estudo.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.exceptions.EntityNotFoundException;
import com.estudo.biblioteca.repositories.AutorRepository;

@Service
public class AutorService {

  @Autowired
  private AutorRepository autorRepository;

  private Autor update(long id, AutorDTO autorDTO) {
    Autor entity = autorDTO.toEntity();
    entity.setId(id);
    return entity;
  }

  @Transactional(readOnly = true)
  public List<AutorDTO> getAll() {
    List<Autor> result = autorRepository.findAll();
    // List<AutorDTO> dto = result.stream().map(autor -> new
    // AutorDTO(autor)).toList();
    List<AutorDTO> dto = result.stream().map(AutorDTO::new).toList();
    return dto;
  }

  @Transactional(readOnly = true)
  public AutorDTO findById(long id) {
    Autor result = autorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("ID Not Found: " + id));
    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

  @Transactional
  public AutorDTO createAutor(AutorDTO autorDTO) {
    Autor entity = autorDTO.toEntity();
    Autor result = autorRepository.save(entity);
    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

  @Transactional
  public AutorDTO updateAutor(long id, AutorDTO autorDTO) {
    Autor entity = update(id, autorDTO);
    Autor result = autorRepository.save(entity);
    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

  @Transactional
  public List<AutorDTO> deleteAutor(long id) {
    autorRepository.deleteById(id);
    List<Autor> result = autorRepository.findAll();
    List<AutorDTO> dto = result.stream().map(AutorDTO::new).toList();
    return dto;
  }

}
