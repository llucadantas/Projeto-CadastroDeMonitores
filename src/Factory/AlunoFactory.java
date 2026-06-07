package Factory;

import Interfaces.PessoaFactory;
import Model.Aluno;

public class AlunoFactory implements PessoaFactory<Aluno> {
    @Override
    public Aluno criarPessoa(String nome, String email, String senha, String matricula) {
        // Usa o construtor exato que você enviou
        return new Aluno(matricula, senha, nome, email);
    }
}