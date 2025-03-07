package com.estudo.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.dtos.LivroRequestDTO;
import com.estudo.biblioteca.services.LivroService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/livro")
public class LivroController {

  @Autowired
  private LivroService livroService;

  @GetMapping()
  public ResponseEntity<List<LivroDTO>> findAllLivros() {
    List<LivroDTO> result = livroService.findAll();
    return ResponseEntity.ok(result);
  }

  @GetMapping("/page")
  public ResponseEntity<Page<LivroDTO>> findAllPageable(Pageable pageable) {
    Page<LivroDTO> result = livroService.findAllPageable(pageable);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/page/titulo")
  public ResponseEntity<Page<LivroDTO>> findByParamTitle(@RequestParam String titulo, Pageable pageable) {
    Page<LivroDTO> result = livroService.findByParamTitle(titulo, pageable);
    return ResponseEntity.ok(result);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<LivroDTO> findLivrosById(@PathVariable Long id) {
    LivroDTO result = livroService.findById(id);
    return ResponseEntity.ok(result);
  }

  @PostMapping()
  public ResponseEntity<LivroDTO> createLivro(@RequestBody LivroRequestDTO livroRequestDTO) {
    LivroDTO result = livroService.createLivro(livroRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<LivroDTO> updateLivro(@PathVariable Long id,
      @RequestBody LivroRequestDTO livroRequestDTO) {
    LivroDTO result = livroService.updateLivro(id, livroRequestDTO);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<List<LivroDTO>> deleteLivro(@PathVariable Long id) {
    List<LivroDTO> result = livroService.deleteLivro(id);
    return ResponseEntity.ok(result);
  }

}
