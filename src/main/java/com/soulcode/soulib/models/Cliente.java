package com.soulcode.soulib.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // indica para o Spring que esta classe é uma entidade/tabela
@Table(name = "clientes") // nome customizado para a tabela
public class Cliente {
    // @GeneratedValue = determina a estratégia para gerar o ID da tabela
    // IDENTITY => auto incremento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(nullable = false, length = 100)
    private String nome; // VARCHAR(100) NOT NULL

    @Column(nullable = false, length = 42, unique = true)
    private String telefone; // VARCHAR(42) UNIQUE NOT NULL

    @Column(length = 120, unique = true)
    private String email; // VARCHAR(120) UNIQUE

    public Cliente() {
        // Caso você use mais de um construtor, coloque
        // o construtor
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

// Em entidades, não usamos os tipos primitivos:
// Classe invólucra
// -> int -> Integer
// -> double -> Double
// -> boolean -> Boolean
// -> char -> Char
// -> long -> Long