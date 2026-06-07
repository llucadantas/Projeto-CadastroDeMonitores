package Factory;

import Exceptions.ValidacaoException;
import Interfaces.PessoaFactory;
import Model.Coordenador;

public class CoordenadorFactory extends PessoaFactory<Coordenador> {

    @Override
    public Coordenador criarPessoa(String nome, String email, String senha, String matricula) throws ValidacaoException {

        // 1. Reaproveita o código da classe pai
        validarDadosComuns(nome, email, senha);

        // 2. Instanciação segura (Coordenador não utiliza matrícula)
        return new Coordenador(senha, nome, email);
    }
}