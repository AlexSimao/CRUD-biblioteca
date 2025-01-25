package com.estudo.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudo.biblioteca.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
