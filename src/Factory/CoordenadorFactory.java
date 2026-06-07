package Factory;

import Interfaces.PessoaFactory;
import Model.Coordenador;

public class CoordenadorFactory implements PessoaFactory<Coordenador> {
    @Override
    public Coordenador criarPessoa(String nome, String email, String senha, String parametroExtra) {
        // Coordenador não usa matrícula, ignoramos o último parâmetro
        return new Coordenador(senha, nome, email);
    }
}