package Service;

import Exceptions.CadastroException;
import Interfaces.CadastroInterface;
import Model.Aluno;
import Interfaces.AlunoRepository;
// O ideal seria que a interface CoordenadorRepository tivesse o método cadastrar.
// Como colocamos ele apenas na implementação (CoordenadorRepositoryImp),
// vamos instanciar a interface aqui, mas você precisa garantir que ela tenha o método.


public class CadastroAluno implements CadastroInterface<Aluno> {

    private final AlunoRepository alunoRepo;

    public CadastroAluno(AlunoRepository alunoRepo) {
        this.alunoRepo = alunoRepo;
    }

    public boolean cadastro(Aluno a) throws CadastroException {
        if (a.getNome().trim().isEmpty() || a.getMatricula().trim().isEmpty() || a.getSenha().trim().isEmpty()) {
            throw new CadastroException("Todos os campos precisam ser preenchidos");
        }

        // Substituindo a busca da central pela busca do repositório
        if (alunoRepo.recuperarAlunoPorMatricula(a.getMatricula().trim()) != null) {
            throw new CadastroException("Matrícula já cadastrada");
        }

        alunoRepo.adicionarAluno(a);
        return true;
    }


}