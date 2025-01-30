package com.estudo.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudo.biblioteca.entities.Emprestimo;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
  List<Emprestimo> findByDataDevolucaoIsNull();
}
