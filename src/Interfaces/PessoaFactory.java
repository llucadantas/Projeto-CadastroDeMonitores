package Interfaces;

import Exceptions.ValidacaoException;
import Model.Pessoa;

public abstract class PessoaFactory<T extends Pessoa> {

    // Método protegido: as filhas podem usar, mas o mundo externo não.
    protected void validarDadosComuns(String nome, String email, String senha) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Digite seu nome.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new ValidacaoException("Email fora de padrão.");
        }
        if (senha == null || senha.length() < 7) {
            throw new ValidacaoException("Senha menor que 7 caracteres.");
        }
    }

    // O contrato que obriga as filhas a implementarem a criação
    public abstract T criarPessoa(String nome, String email, String senha, String matricula) throws ValidacaoException;
}