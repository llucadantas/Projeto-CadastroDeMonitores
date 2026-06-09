package Factory;

import Exceptions.ValidacaoException;
import Model.Coordenador;

public class CoordenadorFactory extends PessoaFactory<Coordenador> {

    @Override
    public Coordenador criarPessoa(String nome, String email, String senha, String matricula) throws ValidacaoException {

        validarDadosComuns(email, senha);

        return new Coordenador(senha, nome, email);
    }
}