package com.example.salary_calculation.service;

import com.example.salary_calculation.exception.PessoaNotFoundException;
import com.example.salary_calculation.model.Pessoa;
import com.example.salary_calculation.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();
        List<Pessoa> pessoas = Arrays.asList(pessoa1, pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> result = pessoaService.findAll();

        assertEquals(2, result.size());
        verify(pessoaRepository).findAll();
    }

    @Test
    void testFindByIdOrThrow_Found() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);

        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.findByIdOrThrow(1);

        assertEquals(pessoa, result);
        verify(pessoaRepository).findById(1);
    }

    @Test
    void testFindByIdOrThrow_NotFound() {
        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());

        PessoaNotFoundException thrown = assertThrows(
                PessoaNotFoundException.class,
                () -> pessoaService.findByIdOrThrow(1)
        );

        assertEquals("Pessoa não encontrada com o id: 1", thrown.getMessage());
        verify(pessoaRepository).findById(1);
    }

    @Test
    void testSave() {
        Pessoa pessoa = new Pessoa();
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa result = pessoaService.save(pessoa);

        assertEquals(pessoa, result);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void testDeleteById_Found() {
        when(pessoaRepository.existsById(1)).thenReturn(true);

        pessoaService.deleteById(1);

        verify(pessoaRepository).existsById(1);
        verify(pessoaRepository).deleteById(1);
    }

    @Test
    void testDeleteById_NotFound() {
        when(pessoaRepository.existsById(1)).thenReturn(false);

        PessoaNotFoundException thrown = assertThrows(
                PessoaNotFoundException.class,
                () -> pessoaService.deleteById(1)
        );

        assertEquals("Pessoa não encontrada com o id: 1", thrown.getMessage());
        verify(pessoaRepository).existsById(1);
        verify(pessoaRepository, never()).deleteById(1);
    }

    @Test
    void testUpdate() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNome("Old Name");

        Pessoa updatedDetails = new Pessoa();
        updatedDetails.setNome("New Name");

        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa result = pessoaService.update(1, updatedDetails);

        assertEquals("New Name", result.getNome());
        verify(pessoaRepository).findById(1);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void testUpdate_NotFound() {
        Pessoa updatedDetails = new Pessoa();

        when(pessoaRepository.findById(1)).thenReturn(Optional.empty());

        PessoaNotFoundException thrown = assertThrows(
                PessoaNotFoundException.class,
                () -> pessoaService.update(1, updatedDetails)
        );

        assertEquals("Pessoa não encontrada com o id: 1", thrown.getMessage());
        verify(pessoaRepository).findById(1);
        verify(pessoaRepository, never()).save(any());
    }
}
