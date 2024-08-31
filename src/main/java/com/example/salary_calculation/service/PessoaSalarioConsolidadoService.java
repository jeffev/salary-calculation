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

    @Async
    @Transactional
    public void gerarDadosSalariosConsolidado() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        Map<Integer, PessoaSalarioConsolidado> consolidadoMap = pessoaSalarioConsolidadoRepository.findAll().stream()
                .collect(Collectors.toMap(PessoaSalarioConsolidado::getPessoaId, consolidado -> consolidado));

        List<PessoaSalarioConsolidado> toSaveList = pessoas.stream().map(pessoa -> {
            PessoaSalarioConsolidado consolidado = consolidadoMap.get(pessoa.getId());
            if (consolidado == null) {
                consolidado = new PessoaSalarioConsolidado();
            }

            if (pessoa.getCargo() == null) {
                consolidado.setPessoaId(pessoa.getId());
                consolidado.setNomePessoa(pessoa.getNome());
                consolidado.setNomeCargo("Cargo não definido");
                consolidado.setSalario(0.0);
            } else {
                List<CargoVencimento> cargoVencimentos = cargoVencimentoService.findByCargoId(pessoa.getCargo().getId());

                double salarioTotal = cargoVencimentos.stream()
                        .mapToDouble(cargoVencimento -> {
                            double valor = cargoVencimento.getVencimento().getValor();
                            return cargoVencimento.getVencimento().getTipo() == TipoVencimento.CREDITO ? valor : -valor;
                        })
                        .sum();

                consolidado.setPessoaId(pessoa.getId());
                consolidado.setNomePessoa(pessoa.getNome());
                consolidado.setNomeCargo(pessoa.getCargo().getNome());
                consolidado.setSalario(salarioTotal);
            }

            return consolidado;
        }).collect(Collectors.toList());

        pessoaSalarioConsolidadoRepository.saveAll(toSaveList);
    }
}
