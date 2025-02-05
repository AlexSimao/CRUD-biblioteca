package com.estudo.biblioteca.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudo.biblioteca.controllers.EmprestimoController;
import com.estudo.biblioteca.dtos.EmprestimoDTO;
import com.estudo.biblioteca.dtos.EmprestimoRequestDTO;
import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.entities.Emprestimo;
import com.estudo.biblioteca.infra.exceptions.EntityNotFoundException;
import com.estudo.biblioteca.repositories.EmprestimoRepository;

@Service
public class EmprestimoService {

  @Autowired
  private EmprestimoRepository emprestimoRepository;

  @Autowired
  private LivroService livroService;

  private String getDate() {
    String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    return data;
  }

  private Emprestimo toEntity(EmprestimoRequestDTO emprestimoRequestDTO) {
    LivroDTO livro = livroService.findById(emprestimoRequestDTO.getLivro_id());
    Emprestimo emprestimo = new Emprestimo();
    BeanUtils.copyProperties(emprestimoRequestDTO, emprestimo);
    emprestimo.setLivro(livro.toEntity());
    return emprestimo;
  }

  // Adicionando link HATEOAS
  private void addLinkAll(EmprestimoDTO dto) {
    Link linkAll = linkTo(methodOn(EmprestimoController.class).findAll()).withRel("all");
    // dto.removeLinks();
    dto.add(linkAll);
  }

  @Transactional(readOnly = true)
  public List<EmprestimoDTO> findAll() {
    List<Emprestimo> result = emprestimoRepository.findAll();
    List<EmprestimoDTO> dto = result.stream().map(EmprestimoDTO::new).toList();
    return dto;
  }

  @Transactional(readOnly = true)
  public Page<EmprestimoDTO> findAllPageable(Pageable pageable) {
    Page<Emprestimo> result = emprestimoRepository.findAll(pageable);
    Page<EmprestimoDTO> dto = result.map(EmprestimoDTO::new);
    return dto;
  }

  @Transactional(readOnly = true)
  public EmprestimoDTO findById(Long id) {
    Emprestimo result = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Não Existe emprestimo com id: " + id));

    EmprestimoDTO dto = new EmprestimoDTO(result);
    addLinkAll(dto);
    return dto;
  }

  @Transactional(readOnly = true)
  public List<EmprestimoDTO> emprestimoAtivo() {
    List<Emprestimo> result = emprestimoRepository.findByDataDevolucaoIsNull();
    List<EmprestimoDTO> dto = result.stream().map(EmprestimoDTO::new).toList();
    return dto;
  }

  @Transactional
  public EmprestimoDTO novoEmprestimo(EmprestimoRequestDTO emprestimoRequestDTO) {
    emprestimoRequestDTO.setDataEmprestimo(getDate());
    Emprestimo result = emprestimoRepository.save(toEntity(emprestimoRequestDTO));
    EmprestimoDTO dto = new EmprestimoDTO(result);
    addLinkAll(dto);
    return dto;
  }

  @Transactional
  public EmprestimoDTO devolucaoDeEmprestimo(Long id) {
    Emprestimo entity = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Não Existe emprestimo com id: " + id));

    // Verifica se produto tem data de devolução.
    if (entity.getDataDevolucao() != null) {
      var dto = new EmprestimoDTO(entity);
      addLinkAll(dto);
      return dto;
    }

    entity.setDataDevolucao(getDate());
    Emprestimo result = emprestimoRepository.save(entity);
    EmprestimoDTO dto = new EmprestimoDTO(result);
    addLinkAll(dto);
    return dto;
  }

}
