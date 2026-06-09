package Service;

import Exceptions.CadastroException;
import Exceptions.ValidacaoException;
import Interfaces.AlunoRepository;
import Interfaces.CadastroInterface;
import Model.Coordenador;
import Interfaces.CoordenadorRepository;
import Validation.ValidacaoEmail;
import Validation.ValidacaoEmailExistente;
import Validation.ValidacaoSenha;

// O ideal seria que a interface CoordenadorRepository tivesse o método cadastrar.
// Como colocamos ele apenas na implementação (CoordenadorRepositoryImp),
// vamos instanciar a interface aqui, mas você precisa garantir que ela tenha o método.

public class CadastroCoordenador implements CadastroInterface<Coordenador> {

    private final CoordenadorRepository coordRepo;
    private final AlunoRepository alunoRepo;

    public CadastroCoordenador(CoordenadorRepository coordRepo, AlunoRepository alunoRepo) {
        this.coordRepo = coordRepo;
        this.alunoRepo = alunoRepo;
    }


    public boolean cadastro(Coordenador c) throws CadastroException {
        try {
            new ValidacaoEmail().validar(c.getEmail());
            new ValidacaoSenha().validar(c.getSenha());

            new ValidacaoEmailExistente(alunoRepo, coordRepo).validar(c.getEmail());

        } catch (ValidacaoException e) {
            throw new CadastroException(e.getMessage());
        }

        coordRepo.cadastrarCoordenador(c);
        return true;
    }

}