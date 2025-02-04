package com.estudo.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.infra.exceptions.EntityNotFoundException;
import com.estudo.biblioteca.infra.exceptions.ForeignKeyReferenceException;
import com.estudo.biblioteca.repositories.AutorRepository;
import com.estudo.biblioteca.repositories.LivroRepository;

@Service
public class AutorService {

  @Autowired
  private AutorRepository autorRepository;

  @Autowired
  private LivroRepository livroRepository;

  private Autor update(long id, AutorDTO autorDTO) {
    if (autorDTO.getNome() == null || autorDTO.getNome().isBlank()) {
      throw new IllegalArgumentException("Nome não pode ser vazio ou 'null': " + autorDTO.getNome());
    }

    Autor entity = autorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Autor com id: " + id + " não encontrado."));

    entity = autorDTO.toEntity();
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
        .orElseThrow(() -> new EntityNotFoundException("Autor com id: " + id + " não encontrado."));

    AutorDTO dto = new AutorDTO(result);
    return dto;
  }

  @Transactional
  public AutorDTO createAutor(AutorDTO autorDTO) {
    if (autorDTO.getNome() == null || autorDTO.getNome().isBlank()) {
      throw new IllegalArgumentException("Nome não pode ser vazio ou 'null': " + autorDTO.getNome());
    }

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
    // Verifica se entidade Autor id ^^^^ possui referencia na tabela Livro.
    // Se sim, retorna uma exceção.
    if (livroRepository.countByAutorId(id) > 0) {
      throw new ForeignKeyReferenceException("Autor com id: " + id + " possui referencia em Livro.");
    }
    Autor entity = autorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Autor com id: " + id + " não encontrado."));

    autorRepository.delete(entity);

    List<Autor> result = autorRepository.findAll();
    List<AutorDTO> dto = result.stream().map(AutorDTO::new).toList();
    return dto;
  }

}
