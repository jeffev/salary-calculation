package com.example.salary_calculation.converter;

import com.example.salary_calculation.controller.CargoController;
import com.example.salary_calculation.model.Cargo;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("cargoConverter")
public class CargoConverter implements Converter<Cargo> {

    private final CargoController cargoController;

    public CargoConverter() {
        FacesContext context = FacesContext.getCurrentInstance();
        cargoController = (CargoController) context.getELContext().getELResolver()
                .getValue(context.getELContext(), null, "cargoController");
    }

    @Override
    public Cargo getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            int id = Integer.parseInt(value);
            return cargoController.findCargoById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Cargo cargo) {
        if (cargo == null) {
            return "";
        }

        return String.valueOf(cargo.getId());
    }
}
