package com.example.salary_calculation.repository;

import com.example.salary_calculation.model.CargoVencimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoVencimentoRepository extends JpaRepository<CargoVencimento, Integer> {
    List<CargoVencimento> findByCargoId(int cargoId);
}
