package com.estudo.biblioteca.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.repositories.AutorRepository;
import com.estudo.biblioteca.repositories.LivroRepository;
import com.estudo.biblioteca.infra.exceptions.EntityNotFoundException;
import com.estudo.biblioteca.infra.exceptions.ForeignKeyReferenceException;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class AutorServiceTest {

  @Mock
  private AutorRepository autorRepository;

  @Mock
  private LivroRepository livroRepository;

  @InjectMocks
  private AutorService autorService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateAutor() {
    AutorDTO autorDTO = new AutorDTO();
    autorDTO.setNome("Autor Teste");

    Autor autor = autorDTO.toEntity();
    autor.setId(1L);
    when(autorRepository.save(any(Autor.class))).thenReturn(autor);

    AutorDTO createdAutor = autorService.createAutor(autorDTO);

    Assertions.assertEquals(autorDTO.getNome(), createdAutor.getNome());
    verify(autorRepository, times(1)).save(any(Autor.class));
  }

  @Test
  void testDeleteAutor() {
    long id = 1L;
    Autor autor = new Autor();
    autor.setId(id);

    when(autorRepository.findById(id)).thenReturn(Optional.of(autor));
    when(livroRepository.countByAutorId(id)).thenReturn(0L);

    List<AutorDTO> remainingAutores = autorService.deleteAutor(id);

    verify(autorRepository, times(1)).delete(autor);
    Assertions.assertTrue(remainingAutores.isEmpty());
  }

  @Test
  void testFindById() {
    long id = 1L;
    Autor autor = new Autor();
    autor.setId(id);
    autor.setNome("Autor Teste");

    when(autorRepository.findById(id)).thenReturn(Optional.of(autor));

    AutorDTO foundAutor = autorService.findById(id);

    Assertions.assertEquals("Autor Teste", foundAutor.getNome());
    verify(autorRepository, times(1)).findById(id);
  }

  @Test
  void testFindByParamName() {
    String nome = "Teste";
    PageRequest pageable = PageRequest.of(0, 10);
    Autor autor = new Autor();
    autor.setNome(nome);
    autor.setId(1L);
    Page<Autor> page = new PageImpl<>(Collections.singletonList(autor));

    when(autorRepository.findByNomeContainingIgnoreCase(nome, pageable)).thenReturn(page);

    Page<AutorDTO> result = autorService.findByParamName(nome, pageable);

    Assertions.assertEquals(1, result.getTotalElements());
    Assertions.assertEquals(nome, result.getContent().get(0).getNome());
  }

  @Test
  void testFindLivrosByAutor() {
    long id = 1L;
    Autor autor = new Autor();
    autor.setId(id);

    when(autorRepository.findById(id)).thenReturn(Optional.of(autor));
    when(livroRepository.findByAutor(autor)).thenReturn(Collections.emptyList());

    List<LivroDTO> livros = autorService.findLivrosByAutor(id);

    Assertions.assertTrue(livros.isEmpty());
    verify(autorRepository, times(1)).findById(id);
    verify(livroRepository, times(1)).findByAutor(autor);
  }

  @Test
  void testGetAll() {
    Autor autor = new Autor();
    autor.setNome("Autor Teste");
    autor.setId(1L);

    when(autorRepository.findAll()).thenReturn(Collections.singletonList(autor));

    List<AutorDTO> result = autorService.getAll();

    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("Autor Teste", result.get(0).getNome());
  }

  @Test
  void testGetAllPageable() {
    PageRequest pageable = PageRequest.of(0, 10);
    Autor autor = new Autor();
    autor.setNome("Autor Teste");
    autor.setId(1L);
    Page<Autor> page = new PageImpl<>(Collections.singletonList(autor));

    when(autorRepository.findAll(pageable)).thenReturn(page);

    Page<AutorDTO> result = autorService.getAllPageable(pageable);

    Assertions.assertEquals(1, result.getTotalElements());
    Assertions.assertEquals("Autor Teste", result.getContent().get(0).getNome());
  }

  @Test
  void testUpdateAutor() {
    long id = 1L;
    AutorDTO autorDTO = new AutorDTO();
    autorDTO.setNome("Autor Atualizado");
    autorDTO.setId(id);

    Autor autor = new Autor();
    autor.setId(id);
    autor.setNome("Autor Antigo");

    when(autorRepository.findById(id)).thenReturn(Optional.of(autor));
    when(autorRepository.save(any(Autor.class))).thenReturn(autorDTO.toEntity());

    AutorDTO updatedAutor = autorService.updateAutor(id, autorDTO);

    Assertions.assertEquals("Autor Atualizado", updatedAutor.getNome());
    verify(autorRepository, times(1)).findById(id);
    verify(autorRepository, times(1)).save(any(Autor.class));
  }

  @Test
  void testCreateAutorWithInvalidName() {
    AutorDTO autorDTO = new AutorDTO();
    autorDTO.setNome("");

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      autorService.createAutor(autorDTO);
    });

    verify(autorRepository, never()).save(any(Autor.class));
  }

  @Test
  void testUpdateAutorWithInvalidName() {
    long id = 1L;
    AutorDTO autorDTO = new AutorDTO();
    autorDTO.setNome("");

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      autorService.updateAutor(id, autorDTO);
    });

    verify(autorRepository, never()).save(any(Autor.class));
  }

  @Test
  void testDeleteAutorWithForeignKeyReference() {
    long id = 1L;

    when(livroRepository.countByAutorId(id)).thenReturn(1L);

    Assertions.assertThrows(ForeignKeyReferenceException.class, () -> {
      autorService.deleteAutor(id);
    });

    verify(autorRepository, never()).delete(any(Autor.class));
  }

  @Test
  void testFindByIdNotFound() {
    long id = 1L;

    when(autorRepository.findById(id)).thenReturn(Optional.empty());

    Assertions.assertThrows(EntityNotFoundException.class, () -> {
      autorService.findById(id);
    });

    verify(autorRepository, times(1)).findById(id);
  }
}
