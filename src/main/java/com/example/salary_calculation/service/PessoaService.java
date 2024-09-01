package com.example.salary_calculation.service;

import com.example.salary_calculation.exception.PessoaNotFoundException;
import com.example.salary_calculation.model.Pessoa;
import com.example.salary_calculation.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findByIdOrThrow(int id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com o id: " + id));
    }

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public void deleteById(int id) {
        if (!pessoaRepository.existsById(id)) {
            throw new PessoaNotFoundException("Pessoa não encontrada com o id: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    public Pessoa update(int id, Pessoa pessoaDetails) {
        Pessoa pessoa = findByIdOrThrow(id);
        pessoa.setNome(pessoaDetails.getNome());
        pessoa.setCidade(pessoaDetails.getCidade());
        pessoa.setEmail(pessoaDetails.getEmail());
        pessoa.setCep(pessoaDetails.getCep());
        pessoa.setEndereco(pessoaDetails.getEndereco());
        pessoa.setPais(pessoaDetails.getPais());
        pessoa.setUsuario(pessoaDetails.getUsuario());
        pessoa.setTelefone(pessoaDetails.getTelefone());
        pessoa.setDataNascimento(pessoaDetails.getDataNascimento());
        return pessoaRepository.save(pessoa);
    }
}
