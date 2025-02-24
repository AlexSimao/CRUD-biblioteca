package com.estudo.biblioteca.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

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

import java.util.List;
import java.util.Collections;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class LivroServiceTest {

  @Mock
  private LivroRepository livroRepository;

  @Mock
  private AutorRepository autorRepository;

  @Mock
  private EmprestimoRepository emprestimoRepository;

  @Mock
  private AutorService autorService;

  @InjectMocks
  private LivroService livroService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateLivro() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo("Test Title");
    requestDTO.setAno_publicacao("2023");
    requestDTO.setAutorNome("Test Author");

    AutorDTO autorDTO = new AutorDTO();
    autorDTO.setId(1L);
    autorDTO.setNome(requestDTO.getAutorNome());

    Autor autor = autorDTO.toEntity();

    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Test Title");
    livro.setAutor(autor);

    when(autorService.createAutor(any(AutorDTO.class))).thenReturn(autorDTO);
    when(autorRepository.save(any(Autor.class))).thenReturn(autor);
    when(livroRepository.save(any(Livro.class))).thenReturn(livro);

    LivroDTO result = livroService.createLivro(requestDTO);

    assertNotNull(result);
    assertEquals("Test Title", result.getTitulo());
  }

  @Test
  void testDeleteLivro() {
    Livro livro = new Livro();
    livro.setId(1L);

    when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
    when(emprestimoRepository.countByLivroId(1L)).thenReturn(0L);

    List<LivroDTO> result = livroService.deleteLivro(1L);

    assertNotNull(result);
  }

  @Test
  void testFindAll() {
    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Test Title");

    when(livroRepository.findAll()).thenReturn(Collections.singletonList(livro));

    List<LivroDTO> result = livroService.findAll();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Test Title", result.get(0).getTitulo());
  }

  @Test
  void testFindAllPageable() {
    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Test Title");

    Pageable pageable = PageRequest.of(0, 10);
    Page<Livro> page = new PageImpl<>(Collections.singletonList(livro));

    when(livroRepository.findAll(pageable)).thenReturn(page);

    Page<LivroDTO> result = livroService.findAllPageable(pageable);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals("Test Title", result.getContent().get(0).getTitulo());
  }

  @Test
  void testFindById() {
    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Test Title");

    when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

    LivroDTO result = livroService.findById(1L);

    assertNotNull(result);
    assertEquals("Test Title", result.getTitulo());
  }

  @Test
  void testFindByParamTitle() {
    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Test Title");

    Pageable pageable = PageRequest.of(0, 10);
    Page<Livro> page = new PageImpl<>(Collections.singletonList(livro));

    when(livroRepository.findByTituloContainingIgnoreCase("Test", pageable)).thenReturn(page);

    Page<LivroDTO> result = livroService.findByParamTitle("Test", pageable);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals("Test Title", result.getContent().get(0).getTitulo());
  }

  @Test
  void testUpdateLivro() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo("Updated Title");
    requestDTO.setAno_publicacao("2023");
    requestDTO.setAutorNome("Updated Author");

    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Original Title");

    AutorDTO updatedAutorDTO = new AutorDTO();
    updatedAutorDTO.setId(1L);
    updatedAutorDTO.setNome(requestDTO.getAutorNome());

    when(autorService.createAutor(any(AutorDTO.class))).thenReturn(updatedAutorDTO);
    when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

    // "invocation" == Argumento passado para a chamada save({argumento}).
    when(livroRepository.save(any(Livro.class))).thenAnswer(invocation -> {
      Livro savedLivro = invocation.getArgument(0);
      savedLivro.setTitulo(requestDTO.getTitulo());
      return savedLivro;
    });

    LivroDTO result = livroService.updateLivro(1L, requestDTO);

    assertNotNull(result);
    assertEquals("Updated Title", result.getTitulo());
  }

  @Test
  void testFindByIdNotFound() {
    when(livroRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> livroService.findById(1L));
  }

  @Test
  void testCreateLivroWithExistingAuthor() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo("Test Title");
    requestDTO.setAno_publicacao("2023");
    requestDTO.setAutorNome("Existing Author");

    Autor autor = new Autor();
    autor.setId(1L);
    autor.setNome("Existing Author");

    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Test Title");
    livro.setAutor(autor);

    when(autorRepository.findByNome("Existing Author")).thenReturn(autor);
    when(livroRepository.save(any(Livro.class))).thenReturn(livro);

    LivroDTO result = livroService.createLivro(requestDTO);

    assertNotNull(result);
    assertEquals("Test Title", result.getTitulo());
    assertEquals("Existing Author", result.getAutor().getNome());
  }

  @Test
  void testDeleteLivroWithForeignKeyReference() {
    when(emprestimoRepository.countByLivroId(1L)).thenReturn(1L);

    assertThrows(ForeignKeyReferenceException.class, () -> livroService.deleteLivro(1L));
  }

  @Test
  void testUpdateLivroNotFound() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo("Updated Title");
    requestDTO.setAno_publicacao("2023");
    requestDTO.setAutorNome("Updated Author");

    when(livroRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> livroService.updateLivro(1L, requestDTO));
  }

  @Test
  void testCreateLivroWithNullTitle() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo(null);
    requestDTO.setAno_publicacao("2023");
    requestDTO.setAutorNome("Test Author");

    assertThrows(IllegalArgumentException.class, () -> livroService.createLivro(requestDTO));
  }

  @Test
  void testCreateLivroWithBlankTitle() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo("");
    requestDTO.setAno_publicacao("2023");
    requestDTO.setAutorNome("Test Author");

    assertThrows(IllegalArgumentException.class, () -> livroService.createLivro(requestDTO));
  }

  @Test
  void testCreateLivroWithNullAnoPublicacao() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo("Test Title");
    requestDTO.setAno_publicacao(null);
    requestDTO.setAutorNome("Test Author");

    assertThrows(IllegalArgumentException.class, () -> livroService.createLivro(requestDTO));
  }

  @Test
  void testCreateLivroWithBlankAnoPublicacao() {
    LivroRequestDTO requestDTO = new LivroRequestDTO();
    requestDTO.setTitulo("Test Title");
    requestDTO.setAno_publicacao("");
    requestDTO.setAutorNome("Test Author");

    assertThrows(IllegalArgumentException.class, () -> livroService.createLivro(requestDTO));
  }
}
