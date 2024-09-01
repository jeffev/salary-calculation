package com.example.salary_calculation.controller;

import com.example.salary_calculation.reporting.RelatorioService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;

@Named
@SessionScoped
public class SalariosReportController {

    @Autowired
    private RelatorioService relatorioService;

    public void generateReport() {
        try {
            byte[] reportBytes = relatorioService.generateReport();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=relatorio.pdf");

            try (OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(reportBytes);
                outputStream.flush();
            }

            facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
