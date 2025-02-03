package com.estudo.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estudo.biblioteca.entities.Emprestimo;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
  List<Emprestimo> findByDataDevolucaoIsNull();

  @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.livro.id = :id")
  Long countByLivroId(@Param("id") Long id);
}
