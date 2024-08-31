package com.example.salary_calculation.service;

import com.example.salary_calculation.model.Cargo;
import com.example.salary_calculation.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

}
