package Factory;

import Exceptions.ValidacaoException;
import Interfaces.PessoaFactory;
import Model.Aluno;

public class AlunoFactory extends PessoaFactory<Aluno> {

    @Override
    public Aluno criarPessoa(String nome, String email, String senha, String matricula) throws ValidacaoException {

        // 1. Reaproveita o código da classe pai para nome, email e senha
        validarDadosComuns(nome, email, senha);

        // 2. Validação específica apenas do Aluno
        if (matricula == null || matricula.length() != 7) {
            throw new ValidacaoException("Matrícula inválida. Deve possuir exatamente 7 dígitos.");
        }

        // 3. Instanciação segura
        return new Aluno(matricula, senha, nome, email);
    }
}