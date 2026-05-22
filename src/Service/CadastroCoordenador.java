package Service;

import Exceptions.CadastroException;
import Interfaces.CadastroInterface;
import Model.Coordenador;
import Interfaces.CoordenadorRepository;

// O ideal seria que a interface CoordenadorRepository tivesse o método cadastrar.
// Como colocamos ele apenas na implementação (CoordenadorRepositoryImp),
// vamos instanciar a interface aqui, mas você precisa garantir que ela tenha o método.

public class CadastroCoordenador implements CadastroInterface<Coordenador> {

    private final CoordenadorRepository coordRepo;

    public CadastroCoordenador(CoordenadorRepository coordRepo) {
        this.coordRepo = coordRepo;
    }


    public boolean cadastro(Coordenador c) throws CadastroException {
        if (c.getNome().trim().isEmpty() || c.getSenha().trim().isEmpty() || c.getEmail().trim().isEmpty()) {
            throw new CadastroException("Todos os campos precisam ser preenchidos");
        }

        // Verifica se já existe um coordenador (regra de coordenador único)
        if (coordRepo.getCoordenador() != null) {
            throw new CadastroException("Já existe um coordenador cadastrado no sistema.");
        }

        coordRepo.cadastrarCoordenador(c);
        return true;
    }

}