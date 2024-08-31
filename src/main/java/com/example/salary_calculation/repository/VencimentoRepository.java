package com.example.salary_calculation.repository;

import com.example.salary_calculation.model.Vencimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VencimentoRepository extends JpaRepository<Vencimento, Integer> {
}