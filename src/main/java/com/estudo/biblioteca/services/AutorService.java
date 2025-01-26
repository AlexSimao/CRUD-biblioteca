package com.estudo.biblioteca.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.repositories.AutorRepository;

@Service
public class AutorService {

  @Autowired
  private AutorRepository autorRepository;

  private Autor toEntity(AutorDTO autorDTO) {
    Autor entity = new Autor();
    BeanUtils.copyProperties(autorDTO, entity);
    return entity;
  }

  private Autor update(long id, AutorDTO autorDTO) {
    Autor entity = toEntity(autorDTO);
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
  public AutorDTO findById(@PathVariable long id) {
    Autor result = autorRepository.findById(id).get();
    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

  @Transactional
  public AutorDTO createAutor(@RequestBody AutorDTO autorDTO) {
    Autor entity = toEntity(autorDTO);
    Autor result = autorRepository.save(entity);
    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

  @Transactional
  public AutorDTO updateAutor(@PathVariable long id, @RequestBody AutorDTO autorDTO) {
    Autor entity = update(id, autorDTO);
    Autor result = autorRepository.save(entity);
    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

  @Transactional
  public List<AutorDTO> deleteAutor(@PathVariable long id) {
    autorRepository.deleteById(id);
    List<Autor> result = autorRepository.findAll();
    List<AutorDTO> dto = result.stream().map(AutorDTO::new).toList();
    return dto;
  }

}
