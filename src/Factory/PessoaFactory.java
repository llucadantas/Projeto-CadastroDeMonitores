package Factory;

import Exceptions.ValidacaoException;
import Model.Pessoa;
import Validation.*;

public abstract class PessoaFactory<T extends Pessoa> {

    protected void validarDadosComuns(String email, String senha) throws ValidacaoException {
        new ValidacaoEmail().validar(email);
        new ValidacaoSenha().validar(senha);
    }
    public abstract T criarPessoa(String nome, String email, String senha, String matricula) throws ValidacaoException;
}