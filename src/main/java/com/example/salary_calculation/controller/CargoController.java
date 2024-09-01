package com.example.salary_calculation.controller;

import com.example.salary_calculation.model.Cargo;
import com.example.salary_calculation.service.CargoService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Named
@SessionScoped
public class CargoController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CargoService cargoService;

    public Cargo findCargoById(int id) {
        return cargoService.findById(id);
    }
}
