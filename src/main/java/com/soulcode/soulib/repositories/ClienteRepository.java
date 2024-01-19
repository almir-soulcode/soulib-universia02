package com.soulcode.soulib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soulcode.soulib.models.Cliente;

// Nomenclatura: Nome da entidade + Repository

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Um repository simplifica a interação com o banco de dados. Disponibilizando
    // uma interface que possui métodos básicos como listar, salvar e deletar.
    // Operações: save, findById, findAll, delete
}
