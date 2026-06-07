package Interfaces;

import Model.Pessoa;

// Interface genérica para as fábricas de criação (Factory Method)
public interface PessoaFactory<T extends Pessoa> {
    T criarPessoa(String nome, String email, String senha, String parametroExtra);
}