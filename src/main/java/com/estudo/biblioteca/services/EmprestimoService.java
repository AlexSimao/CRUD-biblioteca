package com.estudo.biblioteca.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.estudo.biblioteca.dtos.EmprestimoDTO;
import com.estudo.biblioteca.dtos.LivroDTO;
import com.estudo.biblioteca.entities.Emprestimo;
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

  private Emprestimo toEntity(EmprestimoDTO emprestimoDTO) {
    LivroDTO livro = livroService.findById(emprestimoDTO.getLivro_id());
    Emprestimo emprestimo = new Emprestimo();
    BeanUtils.copyProperties(emprestimoDTO, emprestimo);
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
  public EmprestimoDTO findById(@PathVariable Long id) {
    Emprestimo result = emprestimoRepository.findById(id).get();
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
  public EmprestimoDTO novoEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
    emprestimoDTO.setDataEmprestimo(getDate());
    Emprestimo result = emprestimoRepository.save(toEntity(emprestimoDTO));
    EmprestimoDTO dto = new EmprestimoDTO(result);
    return dto;
  }

  @Transactional
  public EmprestimoDTO devolucaoDeEmprestimo(@PathVariable Long id) {
    Emprestimo entity = emprestimoRepository.findById(id).get();

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
