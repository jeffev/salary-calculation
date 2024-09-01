package com.example.salary_calculation.service;

import com.example.salary_calculation.model.Cargo;
import com.example.salary_calculation.model.CargoVencimento;
import com.example.salary_calculation.model.Pessoa;
import com.example.salary_calculation.model.PessoaSalarioConsolidado;
import com.example.salary_calculation.model.TipoVencimento;
import com.example.salary_calculation.model.Vencimento;
import com.example.salary_calculation.repository.PessoaRepository;
import com.example.salary_calculation.repository.PessoaSalarioConsolidadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaSalarioConsolidadoServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaSalarioConsolidadoRepository pessoaSalarioConsolidadoRepository;

    @Mock
    private CargoVencimentoService cargoVencimentoService;

    @InjectMocks
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<PessoaSalarioConsolidado> consolidadoList = new ArrayList<>();
        when(pessoaSalarioConsolidadoRepository.findAll()).thenReturn(consolidadoList);

        List<PessoaSalarioConsolidado> result = pessoaSalarioConsolidadoService.findAll();

        assertNotNull(result);
        assertEquals(consolidadoList, result);
        verify(pessoaSalarioConsolidadoRepository).findAll();
    }

    @Test
    void testCalculaSalario() {
        int cargoId = 1;
        List<CargoVencimento> cargoVencimentos = new ArrayList<>();
        Vencimento vencimento1 = new Vencimento();
        vencimento1.setValor(1000);
        vencimento1.setTipo(TipoVencimento.CREDITO);

        Vencimento vencimento2 = new Vencimento();
        vencimento2.setValor(200);
        vencimento2.setTipo(TipoVencimento.DEBITO);

        CargoVencimento cargoVencimento1 = new CargoVencimento();
        cargoVencimento1.setVencimento(vencimento1);

        CargoVencimento cargoVencimento2 = new CargoVencimento();
        cargoVencimento2.setVencimento(vencimento2);

        cargoVencimentos.add(cargoVencimento1);
        cargoVencimentos.add(cargoVencimento2);

        when(cargoVencimentoService.findByCargoId(cargoId)).thenReturn(cargoVencimentos);

        double salario = pessoaSalarioConsolidadoService.calculaSalario(cargoId);

        assertEquals(800, salario); // 1000 (CRÉDITO) - 200 (DÉBITO) = 800
    }

    @Test
    void testCriaPessoaSalarioConsolidado_ComCargo() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João");
        Cargo cargo = new Cargo();
        cargo.setId(1);
        cargo.setNome("Desenvolvedor");
        pessoa.setCargo(cargo);

        PessoaSalarioConsolidado consolidado = new PessoaSalarioConsolidado();

        when(cargoVencimentoService.findByCargoId(cargo.getId())).thenReturn(new ArrayList<>());

        PessoaSalarioConsolidado result = pessoaSalarioConsolidadoService.criaPessoaSalarioConsolidado(pessoa, consolidado);

        assertEquals(pessoa.getId(), result.getPessoaId());
        assertEquals(pessoa.getNome(), result.getNomePessoa());
        assertEquals(pessoa.getCargo().getNome(), result.getNomeCargo());
        assertEquals(0.0, result.getSalario()); // Since no salary components were returned
    }

    @Test
    void testCriaPessoaSalarioConsolidado_SemCargo() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João");

        PessoaSalarioConsolidado consolidado = new PessoaSalarioConsolidado();

        PessoaSalarioConsolidado result = pessoaSalarioConsolidadoService.criaPessoaSalarioConsolidado(pessoa, consolidado);

        assertEquals(pessoa.getId(), result.getPessoaId());
        assertEquals(pessoa.getNome(), result.getNomePessoa());
        assertEquals("Cargo não definido", result.getNomeCargo());
        assertEquals(0.0, result.getSalario());
    }

    @Test
    void testGerarDadosSalariosConsolidado() {
        List<Pessoa> pessoas = new ArrayList<>();
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("João");
        pessoas.add(pessoa);

        when(pessoaRepository.findAll()).thenReturn(pessoas);
        when(pessoaSalarioConsolidadoRepository.findAll()).thenReturn(new ArrayList<>());

        pessoaSalarioConsolidadoService.gerarDadosSalariosConsolidado();

        verify(pessoaSalarioConsolidadoRepository).saveAll(anyList());
    }
}
