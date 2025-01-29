package com.estudo.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudo.biblioteca.entities.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

}
