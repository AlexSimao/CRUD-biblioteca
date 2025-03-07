package com.estudo.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.services.AutorService;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {

  @Autowired
  private AutorService autorService;

  @GetMapping
  public ResponseEntity<List<AutorDTO>> getAll() {
    List<AutorDTO> result = autorService.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/page")
  public ResponseEntity<Page<AutorDTO>> getAllPageable(Pageable pageable) {
    Page<AutorDTO> result = autorService.getAllPageable(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/page/nome")
  public ResponseEntity<Page<AutorDTO>> findByParamName(@RequestParam String nome, Pageable pageable) {
    Page<AutorDTO> result = autorService.findByParamName(nome, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<AutorDTO> findById(@PathVariable long id) {
    AutorDTO result = autorService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping(value = "/{id}/livro")
  public ResponseEntity<List<LivroDTO>> findLivrosByAutor(@PathVariable long id) {
    List<LivroDTO> result = autorService.findLivrosByAutor(id);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping()
  public ResponseEntity<AutorDTO> createAutor(@RequestBody AutorDTO autorDTO) {
    AutorDTO result = autorService.createAutor(autorDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<AutorDTO> updateAutor(@PathVariable Long id, @RequestBody AutorDTO autorDTO) {
    AutorDTO result = autorService.updateAutor(id, autorDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<List<AutorDTO>> deleteAutor(@PathVariable Long id) {
    List<AutorDTO> result = autorService.deleteAutor(id);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
