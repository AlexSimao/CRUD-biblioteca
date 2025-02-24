package com.estudo.biblioteca.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.estudo.biblioteca.dtos.EmprestimoDTO;
import com.estudo.biblioteca.dtos.EmprestimoRequestDTO;
import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.entities.Emprestimo;
import com.estudo.biblioteca.entities.Livro;
import com.estudo.biblioteca.infra.exceptions.EntityNotFoundException;
import com.estudo.biblioteca.repositories.EmprestimoRepository;

public class EmprestimoServiceTest {

  @Mock
  private EmprestimoRepository emprestimoRepository;

  @Mock
  private LivroService livroService;

  @InjectMocks
  private EmprestimoService emprestimoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testDevolucaoDeEmprestimo() {
    Emprestimo emprestimo = new Emprestimo();
    emprestimo.setId(1L);
    when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));
    when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(emprestimo);

    EmprestimoDTO result = emprestimoService.devolucaoDeEmprestimo(1L);
    assertNotNull(result);
    assertEquals(1L, result.getId());
  }

  @Test
  void testEmprestimoAtivo() {
    when(emprestimoRepository.findByDataDevolucaoIsNull()).thenReturn(Collections.singletonList(new Emprestimo()));

    var result = emprestimoService.emprestimoAtivo();
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  void testFindAll() {
    when(emprestimoRepository.findAll()).thenReturn(Collections.singletonList(new Emprestimo()));

    var result = emprestimoService.findAll();
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  void testFindAllPageable() {
    Page<Emprestimo> page = new PageImpl<>(Collections.singletonList(new Emprestimo()));
    when(emprestimoRepository.findAll(any(PageRequest.class))).thenReturn(page);

    var result = emprestimoService.findAllPageable(PageRequest.of(0, 10));
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
  }

  @Test
  void testFindById() {
    Emprestimo emprestimo = new Emprestimo();
    emprestimo.setId(1L);
    when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));

    var result = emprestimoService.findById(1L);
    assertNotNull(result);
    assertEquals(1L, result.getId());
  }

  @Test
  void testNovoEmprestimo() {
    EmprestimoRequestDTO emprestimoRequestDTO = new EmprestimoRequestDTO();
    emprestimoRequestDTO.setLivro_id(1L);
    Livro livro = new Livro();
    when(livroService.findById(1L)).thenReturn(new LivroDTO(livro));
    when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(new Emprestimo());

    var result = emprestimoService.novoEmprestimo(emprestimoRequestDTO);
    assertNotNull(result);
  }

  @Test
  void testDevolucaoDeEmprestimoNotFound() {
    when(emprestimoRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> emprestimoService.devolucaoDeEmprestimo(1L));
  }

  @Test
  void testFindByIdNotFound() {
    when(emprestimoRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> emprestimoService.findById(1L));
  }

  @Test
  void testNovoEmprestimoLivroNotFound() {
    EmprestimoRequestDTO emprestimoRequestDTO = new EmprestimoRequestDTO();
    emprestimoRequestDTO.setLivro_id(1L);
    when(livroService.findById(1L)).thenThrow(new EntityNotFoundException("Livro não encontrado"));

    assertThrows(EntityNotFoundException.class, () -> emprestimoService.novoEmprestimo(emprestimoRequestDTO));
  }

  @Test
  void testDevolucaoDeEmprestimoAlreadyReturned() {
    Emprestimo emprestimo = new Emprestimo();
    emprestimo.setId(1L);
    emprestimo.setDataDevolucao("01-01-2023");
    when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));

    EmprestimoDTO result = emprestimoService.devolucaoDeEmprestimo(1L);
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("01-01-2023", result.getDataDevolucao());
  }

  @Test
  void testNovoEmprestimoWithDate() {
    EmprestimoRequestDTO emprestimoRequestDTO = new EmprestimoRequestDTO();
    emprestimoRequestDTO.setLivro_id(1L);
    Livro livro = new Livro();
    when(livroService.findById(1L)).thenReturn(new LivroDTO(livro));
    when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(new Emprestimo());

    var result = emprestimoService.novoEmprestimo(emprestimoRequestDTO);
    assertNotNull(result);
    verify(emprestimoRepository).save(any(Emprestimo.class));
  }
}
