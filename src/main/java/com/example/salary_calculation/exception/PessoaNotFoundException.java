package com.example.salary_calculation.exception;

public class PessoaNotFoundException extends RuntimeException {
    public PessoaNotFoundException(String message) {
        super(message);
    }
}
