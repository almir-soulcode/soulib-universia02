package com.soulcode.soulib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soulcode.soulib.models.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

}