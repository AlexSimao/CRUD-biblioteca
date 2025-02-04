package com.estudo.biblioteca.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(readOnly = true)
  public List<EmprestimoDTO> findAll() {
    List<Emprestimo> result = emprestimoRepository.findAll();
    List<EmprestimoDTO> dto = result.stream().map(EmprestimoDTO::new).toList();
    return dto;
  }

  @Transactional(readOnly = true)
  public EmprestimoDTO findById(Long id) {
    Emprestimo result = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Não Existe emprestimo com id: " + id));

    EmprestimoDTO dto = new EmprestimoDTO(result);
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
    return dto;
  }

  @Transactional
  public EmprestimoDTO devolucaoDeEmprestimo(Long id) {
    Emprestimo entity = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Não Existe emprestimo com id: " + id));

    // Verifica se produto tem data de devolução.
    if (entity.getDataDevolucao() != null) {
      return new EmprestimoDTO(entity);
    }

    entity.setDataDevolucao(getDate());
    Emprestimo result = emprestimoRepository.save(entity);
    EmprestimoDTO dto = new EmprestimoDTO(result);
    return dto;
  }

}
