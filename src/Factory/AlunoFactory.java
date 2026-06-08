package Factory;

import Exceptions.ValidacaoException;
import Model.Aluno;
import Validation.ValidacaoMatricula;

public class AlunoFactory extends PessoaFactory<Aluno> {

    @Override
    public Aluno criarPessoa(String nome, String email, String senha, String matricula) throws ValidacaoException {

        validarDadosComuns(email, senha);

        new ValidacaoMatricula().validar(matricula);

        return new Aluno(matricula, senha, nome, email);
    }
}