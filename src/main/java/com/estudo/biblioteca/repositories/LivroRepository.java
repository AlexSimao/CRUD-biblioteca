package com.estudo.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estudo.biblioteca.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

  @Query("SELECT COUNT(e) FROM Livro e WHERE e.autor.id = :id")
  Long countByAutorId(@Param("id") Long id);
}
