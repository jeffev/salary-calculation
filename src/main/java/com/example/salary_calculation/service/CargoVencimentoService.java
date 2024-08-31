package com.example.salary_calculation.service;

import com.example.salary_calculation.model.CargoVencimento;
import com.example.salary_calculation.repository.CargoVencimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoVencimentoService {

    @Autowired
    private CargoVencimentoRepository cargoVencimentoRepository;

    public List<CargoVencimento> findAll() {
        return cargoVencimentoRepository.findAll();
    }

    public Optional<CargoVencimento> findById(int id) {
        return cargoVencimentoRepository.findById(id);
    }

    public List<CargoVencimento> findByCargoId(int cargoId) {
        return cargoVencimentoRepository.findByCargoId(cargoId);
    }

    public CargoVencimento save(CargoVencimento cargoVencimento) {
        return cargoVencimentoRepository.save(cargoVencimento);
    }

    public void deleteById(int id) {
        cargoVencimentoRepository.deleteById(id);
    }
}
