package com.estudo.biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.biblioteca.dtos.EmprestimoDTO;
import com.estudo.biblioteca.dtos.EmprestimoRequestDTO;
import com.estudo.biblioteca.services.EmprestimoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(value = "/emprestimo")
public class EmprestimoController {

  @Autowired
  private EmprestimoService emprestimoService;

  @GetMapping
  public ResponseEntity<List<EmprestimoDTO>> findAll() {
    List<EmprestimoDTO> result = emprestimoService.findAll();
    return ResponseEntity.ok(result);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<EmprestimoDTO> findById(@PathVariable Long id) {
    EmprestimoDTO result = emprestimoService.findById(id);
    return ResponseEntity.ok(result);
  }

  @GetMapping(value = "/ativos")
  public ResponseEntity<List<EmprestimoDTO>> emprestimoAtivo() {
    List<EmprestimoDTO> result = emprestimoService.emprestimoAtivo();
    return ResponseEntity.ok(result);
  }

  @PostMapping
  public ResponseEntity<EmprestimoDTO> novoEmprestimo(@RequestBody EmprestimoRequestDTO emprestimoDTO) {
    EmprestimoDTO result = emprestimoService.novoEmprestimo(emprestimoDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<EmprestimoDTO> devolucaoDeEmprestimo(@PathVariable Long id) {
    EmprestimoDTO result = emprestimoService.devolucaoDeEmprestimo(id);
    return ResponseEntity.ok(result);
  }

}
