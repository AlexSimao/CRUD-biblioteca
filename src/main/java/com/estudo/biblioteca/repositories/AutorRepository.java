package com.estudo.biblioteca.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estudo.biblioteca.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
  Autor findByNome(String nome);

  @Query("SELECT obj FROM Autor obj WHERE LOWER(obj.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
  Page<Autor> findByParamName(String nome, Pageable pageable);
}
