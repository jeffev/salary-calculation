package com.example.salary_calculation.service;

import com.example.salary_calculation.model.*;
import com.example.salary_calculation.repository.PessoaRepository;
import com.example.salary_calculation.repository.PessoaSalarioConsolidadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PessoaSalarioConsolidadoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaSalarioConsolidadoRepository pessoaSalarioConsolidadoRepository;

    @Autowired
    private CargoVencimentoService cargoVencimentoService;

    public List<PessoaSalarioConsolidado> findAll() {
        return pessoaSalarioConsolidadoRepository.findAll();
    }

    public double calculaSalario(int idCargo) {
        List<CargoVencimento> cargoVencimentos = cargoVencimentoService.findByCargoId(idCargo);

        return cargoVencimentos.stream()
                .mapToDouble(cargoVencimento -> {
                    double valor = cargoVencimento.getVencimento().getValor();
                    return cargoVencimento.getVencimento().getTipo() == TipoVencimento.CREDITO ? valor : -valor;
                })
                .sum();
    }

    public PessoaSalarioConsolidado criaPessoaSalarioConsolidado(Pessoa pessoa, PessoaSalarioConsolidado consolidado) {
        if (pessoa.getCargo() == null) {
            consolidado.setPessoaId(pessoa.getId());
            consolidado.setNomePessoa(pessoa.getNome());
            consolidado.setNomeCargo("Cargo não definido");
            consolidado.setSalario(0.0);
        } else {
            consolidado.setPessoaId(pessoa.getId());
            consolidado.setNomePessoa(pessoa.getNome());
            consolidado.setNomeCargo(pessoa.getCargo().getNome());
            consolidado.setSalario(calculaSalario(pessoa.getCargo().getId()));
        }

        return consolidado;
    }

    @Async
    @Transactional
    public void gerarDadosSalariosConsolidado() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        Set<Integer> idsPessoas = pessoas.stream()
                .map(Pessoa::getId)
                .collect(Collectors.toSet());

        Map<Integer, PessoaSalarioConsolidado> consolidadoMap = pessoaSalarioConsolidadoRepository.findAll().stream()
                .collect(Collectors.toMap(PessoaSalarioConsolidado::getPessoaId, consolidado -> consolidado));

        List<PessoaSalarioConsolidado> toSaveList = pessoas.stream().map(pessoa -> {
            PessoaSalarioConsolidado consolidado = consolidadoMap.get(pessoa.getId());
            if (consolidado == null) {
                consolidado = new PessoaSalarioConsolidado();
            }

            return criaPessoaSalarioConsolidado(pessoa, consolidado);
        }).collect(Collectors.toList());

        pessoaSalarioConsolidadoRepository.saveAll(toSaveList);

        List<PessoaSalarioConsolidado> toDeleteList = consolidadoMap.values().stream()
                .filter(consolidado -> !idsPessoas.contains(consolidado.getPessoaId()))
                .collect(Collectors.toList());

        if (!toDeleteList.isEmpty()) {
            pessoaSalarioConsolidadoRepository.deleteAll(toDeleteList);
        }
    }

}
