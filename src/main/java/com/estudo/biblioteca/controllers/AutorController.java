package com.estudo.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.biblioteca.dtos.AutorDTO;
import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.repositories.AutorRepository;
import com.estudo.biblioteca.services.AutorService;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {

  @Autowired
  private AutorService autorService;

  @Autowired
  private AutorRepository autorRepository;

  @GetMapping
  public ResponseEntity<List<AutorDTO>> getAll() {
    List<AutorDTO> result = autorService.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(result);

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> findById(@PathVariable long id) {
    Optional<Autor> optAutor = autorRepository.findById(id);
    if (optAutor.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");
    }

    AutorDTO result = autorService.findById(id);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
