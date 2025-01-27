package com.estudo.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudo.biblioteca.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
