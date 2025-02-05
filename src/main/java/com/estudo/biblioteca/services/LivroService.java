package com.estudo.biblioteca.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.estudo.biblioteca.controllers.LivroController;
import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.dtos.LivroRequestDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.entities.Livro;
import com.estudo.biblioteca.infra.exceptions.EntityNotFoundException;
import com.estudo.biblioteca.infra.exceptions.ForeignKeyReferenceException;
import com.estudo.biblioteca.repositories.AutorRepository;
import com.estudo.biblioteca.repositories.EmprestimoRepository;
import com.estudo.biblioteca.repositories.LivroRepository;

@Service
public class LivroService {
  @Autowired
  private LivroRepository livroRepository;

  @Autowired
  private AutorRepository autorRepository;

  @Autowired
  private AutorService autorService;

  @Autowired
  private EmprestimoRepository emprestimoRepository;

  private Livro update(Long id, LivroRequestDTO livroRequestDTO) {
    if (livroRequestDTO.getTitulo() == null || livroRequestDTO.getTitulo().isBlank()) {
      throw new IllegalArgumentException(
          "Titulo do Livro não pode ser vazio ou 'null': " + livroRequestDTO.getTitulo());
    }

    Livro entity = livroRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Livro com id: " + id + " não encontrado."));

    LivroDTO livro = livroRequestToLivroDTO(livroRequestDTO);
    BeanUtils.copyProperties(livroRequestDTO, livro);

    entity = livro.toEntity();
    entity.setId(id);
    return entity;
  }

  // Transforma um LivroRequestDTO em LivroDTO.
  private LivroDTO livroRequestToLivroDTO(LivroRequestDTO livroRequestDTO) {
    // Mais que o dia inteiro para fazer essa MERDA toda.
    LivroDTO livro = new LivroDTO();
    // Verifica se autor existe na base de dados e salva em uma varriavel
    Autor autor = autorRepository.findByNome(livroRequestDTO.getAutorNome());

    // Se Autor não existir é criado um novo autor e salvo com o nome que veio na
    // requisição.
    if (autor == null) {
      AutorDTO newAutor = new AutorDTO();
      newAutor.setNome(livroRequestDTO.getAutorNome());
      newAutor = autorService.createAutor(newAutor);

      livro.setAutor(newAutor.toEntity());
      BeanUtils.copyProperties(livroRequestDTO, livro);
      return livro;
    }

    livro.setAutor(autor);
    BeanUtils.copyProperties(livroRequestDTO, livro);
    return livro;
  }

  // Adicionando link HATEOAS
  private void addLinkAll(LivroDTO dto) {
    Link linkAll = linkTo(methodOn(LivroController.class).findAllLivros()).withRel("all");
    // dto.removeLinks();
    dto.add(linkAll);
  }

  @Transactional(readOnly = true)
  public List<LivroDTO> findAll() {
    List<Livro> result = livroRepository.findAll();
    List<LivroDTO> dto = result.stream().map(LivroDTO::new).toList();
    return dto;
  }

  @Transactional(readOnly = true)
  public Page<LivroDTO> findAllPageable(Pageable pageable) {
    Page<Livro> result = livroRepository.findAll(pageable);
    Page<LivroDTO> dto = result.map(LivroDTO::new);
    return dto;
  }

  @Transactional(readOnly = true)
  public Page<LivroDTO> findByParamTitle(String titulo, Pageable pageable) {
    Page<Livro> result = livroRepository.findByParamTitle(titulo, pageable);
    Page<LivroDTO> dto = result.map(LivroDTO::new);
    return dto;
  }

  @Transactional(readOnly = true)
  public LivroDTO findById(long id) {
    Livro result = livroRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Livro com id: " + id + " não encontrado."));

    LivroDTO dto = new LivroDTO(result);
    addLinkAll(dto);
    return dto;
  }

  @Transactional
  public LivroDTO createLivro(LivroRequestDTO livroRequestDTO) {
    if (livroRequestDTO.getTitulo() == null || livroRequestDTO.getTitulo().isBlank()) {
      throw new IllegalArgumentException(
          "Titulo do Livro não pode ser vazio ou 'null': " + livroRequestDTO.getTitulo());
    }
    if (livroRequestDTO.getAno_publicacao() == null || livroRequestDTO.getAno_publicacao().isBlank()) {
      throw new IllegalArgumentException(
          "Ano de Publicação do Livro não pode ser vazio ou 'null': " + livroRequestDTO.getAno_publicacao());
    }

    LivroDTO livro = livroRequestToLivroDTO(livroRequestDTO);
    Livro result = livroRepository.save(livro.toEntity());
    LivroDTO dto = new LivroDTO(result);
    addLinkAll(dto);
    return dto;
  }

  @Transactional
  public LivroDTO updateLivro(Long id, LivroRequestDTO livroRequestDTO) {
    Livro updatedLivro = update(id, livroRequestDTO);
    Livro result = livroRepository.save(updatedLivro);
    LivroDTO dto = new LivroDTO(result);
    addLinkAll(dto);
    return dto;
  }

  @Transactional
  public List<LivroDTO> deleteLivro(long id) {
    // Verifica se entidade Livro id ^^^^ possui referencia na tabela Emprestimo.
    // Se sim, retorna uma exceção.
    if (emprestimoRepository.countByLivroId(id) > 0) {
      throw new ForeignKeyReferenceException("Livro com id: " + id + " possui referencia em Emprestimo.");
    }

    Livro entity = livroRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Livro com id: " + id + " não encontrado."));

    livroRepository.delete(entity);
    List<Livro> result = livroRepository.findAll();
    List<LivroDTO> dto = result.stream().map(LivroDTO::new).toList();
    return dto;
  }

}
