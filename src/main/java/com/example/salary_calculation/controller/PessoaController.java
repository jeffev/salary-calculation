package com.example.salary_calculation.controller;

import com.example.salary_calculation.exception.PessoaNotFoundException;
import com.example.salary_calculation.exception.ValidacaoException;
import com.example.salary_calculation.model.Cargo;
import com.example.salary_calculation.model.Pessoa;
import com.example.salary_calculation.service.CargoService;
import com.example.salary_calculation.service.PessoaService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PessoaController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private CargoService cargoService;

    private Pessoa pessoa;
    private Pessoa selectedPessoa;
    private List<Pessoa> pessoas;
    private List<Cargo> cargos;

    @PostConstruct
    public void init() {
        loadPessoas();
        loadCargos();
        selectedPessoa = new Pessoa();
    }

    public void prepareNewPessoa() throws IOException {
        this.selectedPessoa = new Pessoa();

        FacesContext.getCurrentInstance().getExternalContext().redirect("editar_pessoa.xhtml?faces-redirect=true");
    }

    public void prepareEditPessoa(int id) throws IOException {
        findPessoaById(id);

        FacesContext.getCurrentInstance().getExternalContext().redirect("editar_pessoa.xhtml?faces-redirect=true");
    }

    public void savePessoa() {
        System.out.println("savePessoa");
        System.out.println(selectedPessoa.getNome());
        try {
            if (selectedPessoa != null && selectedPessoa.getId() == 0) {
                pessoaService.save(selectedPessoa);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pessoa salva com sucesso!"));
                pessoas.add(selectedPessoa);
            } else {
                updatePessoa();
            }

            selectedPessoa = new Pessoa();
        } catch (ValidacaoException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar a pessoa: " + e.getMessage(), null));
        }
    }

    public void updatePessoa() {
        if (selectedPessoa != null) {
            try {
                pessoaService.update(selectedPessoa.getId(), selectedPessoa);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pessoa atualizada com sucesso!"));

                pessoas.stream()
                        .filter(p -> p.getId() == selectedPessoa.getId())
                        .findFirst()
                        .ifPresent(p -> {
                            int index = pessoas.indexOf(p);
                            pessoas.set(index, selectedPessoa);
                        });
            } catch (PessoaNotFoundException | ValidacaoException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar a pessoa: " + e.getMessage(), null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, selecione uma pessoa para atualizar.", null));
        }
    }

    public void deletePessoa(int id) {
        try {
            pessoaService.deleteById(id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pessoa excluída com sucesso!"));

            pessoas.removeIf(p -> p.getId() == id);
        } catch (PessoaNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir a pessoa: " + e.getMessage(), null));
        }
    }

    public void loadPessoas() {
        try {
            pessoas = pessoaService.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar pessoas: " + e.getMessage(), null));
        }
    }

    public void loadCargos() {
        try {
            cargos = cargoService.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao carregar cargos: " + e.getMessage(), null));
        }
    }

    public void findPessoaById(int id) {
        try {
            selectedPessoa = pessoaService.findByIdOrThrow(id);
        } catch (PessoaNotFoundException e) {
            selectedPessoa = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        } catch (Exception e) {
            selectedPessoa = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar pessoa: " + e.getMessage(), null));
        }
    }

    // Getters e Setters

    public Pessoa getSelectedPessoa() {
        return selectedPessoa;
    }

    public void setSelectedPessoa(Pessoa selectedPessoa) {
        this.selectedPessoa = selectedPessoa;
        System.out.println("setSelectedPessoa");
        System.out.println(selectedPessoa.getNome());
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

}
