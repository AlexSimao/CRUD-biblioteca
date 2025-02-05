package com.estudo.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.entities.Livro;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

  @Query("SELECT COUNT(e) FROM Livro e WHERE e.autor.id = :id")
  // Verifica se existe autor com id determinado.
  Long countByAutorId(@Param("id") Long id);

  // Lista todos os Livros de um Autor determinado. 
  List<Livro> findByAutor(Autor autor);
}
