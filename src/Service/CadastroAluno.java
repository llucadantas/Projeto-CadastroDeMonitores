package Service;

import Exceptions.CadastroException;
import Exceptions.ValidacaoException;
import Interfaces.CadastroInterface;
import Interfaces.CoordenadorRepository;
import Model.Aluno;
import Interfaces.AlunoRepository;
import Validation.*;
// O ideal seria que a interface CoordenadorRepository tivesse o método cadastrar.
// Como colocamos ele apenas na implementação (CoordenadorRepositoryImp),
// vamos instanciar a interface aqui, mas você precisa garantir que ela tenha o método.


public class CadastroAluno implements CadastroInterface<Aluno> {

    private final AlunoRepository alunoRepo;
    private final CoordenadorRepository coordenadorRepo;

    public CadastroAluno(AlunoRepository alunoRepo, CoordenadorRepository coordenadorRepo) {
        this.alunoRepo = alunoRepo;
        this.coordenadorRepo = coordenadorRepo;
    }

    public boolean cadastro(Aluno a) throws CadastroException {
    	// 1. Validação simples de preenchimento do nome (baseado na classe antiga)
        if (a.getNome() == null || a.getNome().trim().isEmpty()) {
            throw new CadastroException("Digite seu nome.");
        }

        try {
            // 2. Executa as validações de formato básico usando as novas estratégias
            new ValidacaoMatricula().validar(a.getMatricula());
            new ValidacaoEmail().validar(a.getEmail());
            new ValidacaoSenha().validar(a.getSenha());

            // 3. Executa as validações de banco/duplicidade passando os repositórios
            new ValidacaoMatriculaExistente(alunoRepo).validar(a.getMatricula());
            new ValidacaoEmailExistente(alunoRepo, coordenadorRepo).validar(a.getEmail());

        } catch (ValidacaoException e) {
            // Captura o erro da estratégia de validação e repassa como CadastroException
            // para manter o contrato da interface CadastroInterface!
            throw new CadastroException(e.getMessage());
        }

        // 4. Se passou por todas as validações sem cair no catch, adiciona o aluno
        return alunoRepo.adicionarAluno(a);
    }


}