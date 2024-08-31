package com.example.salary_calculation.converter;

import com.example.salary_calculation.model.Cargo;
import com.example.salary_calculation.service.CargoService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;

@FacesConverter("cargoConverter")
public class CargoConverter implements Converter<Cargo> {

    @Autowired
    private CargoService cargoService;

    @Override
    public Cargo getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return cargoService.findById(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Cargo cargo) {
        if (cargo == null) {
            return "";
        }

        return String.valueOf(cargo.getId());
    }
}
