package com.estudo.biblioteca.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estudo.biblioteca.entities.Autor;
import com.estudo.biblioteca.entities.Livro;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

  // Verifica se existe autor com id determinado.
  @Query("SELECT COUNT(e) FROM Livro e WHERE e.autor.id = :id")
  Long countByAutorId(@Param("id") Long id);

  // Lista todos os Livros de um Autor determinado.
  List<Livro> findByAutor(Autor autor);

  // Procura na base de dados todos que contenham os titulos semelhantes.
  // @Query("SELECT obj FROM Livro obj WHERE LOWER(obj.titulo) LIKE
  // LOWER(CONCAT('%', :titulo, '%'))")
  Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}
