package com.estudo.biblioteca.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.entities.Livro;
import com.estudo.biblioteca.repositories.AutorRepository;
import com.estudo.biblioteca.repositories.LivroRepository;

@Service
public class LivroService {
  @Autowired
  private LivroRepository livroRepository;

  @Autowired
  private AutorRepository autorRepository;

  @Autowired
  private AutorService autorService;

  private Livro toEntity(LivroDTO livroDTO) {
    Livro entity = new Livro();
    BeanUtils.copyProperties(livroDTO, entity);
    return entity;
  }

  private Autor toEntity(AutorDTO autorDTO) {
    Autor entity = new Autor();
    BeanUtils.copyProperties(autorDTO, entity);
    return entity;
  }

  private Livro update(Long id, LivroDTO livroDTO) {
    Livro entity = toEntity(livroDTO);
    entity.setId(id);
    return entity;
  }

  @Transactional(readOnly = true)
  public List<LivroDTO> findAll() {
    List<Livro> result = livroRepository.findAll();
    List<LivroDTO> dto = result.stream().map(LivroDTO::new).toList();
    return dto;
  }

  @Transactional(readOnly = true)
  public LivroDTO findById(@PathVariable long id) {
    Livro result = livroRepository.findById(id).get();
    LivroDTO dto = new LivroDTO(result);
    return dto;
  }

  @Transactional
  public LivroDTO createLivro(@RequestBody LivroDTO livroDTO) {
    // Só o dia inteiro para fazer essa MERDA.
    Autor autor = autorRepository.findByNome(livroDTO.getAutor().getNome());
    AutorDTO newAutor = new AutorDTO();
    if (autor == null) {
      if (livroDTO.getAutor().getId() != null) {
        newAutor = autorService.findById(livroDTO.getAutor().getId());
      } else {
        newAutor = autorService.createAutor(new AutorDTO(livroDTO.getAutor()));
      }
    }

    LivroDTO livro = new LivroDTO();
    BeanUtils.copyProperties(livroDTO, livro);
    livro.setAutor(toEntity(newAutor));

    Livro result = livroRepository.save(toEntity(livro));
    LivroDTO dto = new LivroDTO(result);
    return dto;
  }

  @Transactional
  public LivroDTO updateLivro(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
    Livro updatedLivro = update(id, livroDTO);
    Livro result = livroRepository.save(updatedLivro);
    LivroDTO dto = new LivroDTO(result);
    return dto;
  }

  @Transactional
  public List<LivroDTO> deleteLivro(@PathVariable long id) {
    livroRepository.deleteById(id);
    List<Livro> result = livroRepository.findAll();
    List<LivroDTO> dto = result.stream().map(LivroDTO::new).toList();
    return dto;
  }

}
