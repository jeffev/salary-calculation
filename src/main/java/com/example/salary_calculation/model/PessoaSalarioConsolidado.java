package com.example.salary_calculation.model;

import jakarta.persistence.Version;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "pessoa_salario_consolidado")
public class PessoaSalarioConsolidado {

    @Id
    @Column(name = "pessoa_id")
    private int pessoaId;

    @Column(name = "nome_pessoa", nullable = false)
    private String nomePessoa;

    @Column(name = "nome_cargo", nullable = false)
    private String nomeCargo;

    @Column(name = "salario", nullable = false)
    private double salario;

    @Version
    @Column(name = "version")
    private Long version;

    // Getters e Setters

    public int getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(int pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
