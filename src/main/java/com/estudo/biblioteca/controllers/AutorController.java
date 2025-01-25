package com.estudo.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.biblioteca.dtos.AutorDTO;
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

}
