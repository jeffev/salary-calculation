package com.example.salary_calculation.controller;

import com.example.salary_calculation.model.PessoaSalarioConsolidado;
import com.example.salary_calculation.service.PessoaSalarioConsolidadoService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Named
@SessionScoped
public class PessoaSalarioConsolidadoController {

    @Autowired
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    private List<PessoaSalarioConsolidado> listaSalariosConsolidados;

    @PostConstruct
    public void init() {
        loadPessoasConsolidado();
    }

    public void loadPessoasConsolidado() {
        try {
            listaSalariosConsolidados = pessoaSalarioConsolidadoService.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar pessoas consolidado: " + e.getMessage(), null));
        }
    }

    public void gerarDadosSalariosConsolidado() {
        pessoaSalarioConsolidadoService.gerarDadosSalariosConsolidado();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Processo de recalculo finalizado!"));
        loadPessoasConsolidado();
    }

    public List<PessoaSalarioConsolidado> getListaSalariosConsolidados() {
        return listaSalariosConsolidados;
    }

    public void setListaSalariosConsolidados(List<PessoaSalarioConsolidado> listaSalariosConsolidados) {
        this.listaSalariosConsolidados = listaSalariosConsolidados;
    }
}