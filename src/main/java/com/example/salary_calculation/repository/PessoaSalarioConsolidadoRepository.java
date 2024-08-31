package com.example.salary_calculation.repository;

import com.example.salary_calculation.model.PessoaSalarioConsolidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaSalarioConsolidadoRepository extends JpaRepository<PessoaSalarioConsolidado, Integer> {
}
