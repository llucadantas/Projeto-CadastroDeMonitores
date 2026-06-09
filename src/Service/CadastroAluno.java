package Service;

import Exceptions.CadastroException;
import Exceptions.ValidacaoException;
import Interfaces.CadastroInterface;
import Interfaces.CoordenadorRepository;
import Model.Aluno;
import Interfaces.AlunoRepository;
import Validation.*;

public class CadastroAluno implements CadastroInterface<Aluno> {

    private final AlunoRepository alunoRepo;
    private final CoordenadorRepository coordenadorRepo;

    public CadastroAluno(AlunoRepository alunoRepo, CoordenadorRepository coordenadorRepo) {
        this.alunoRepo = alunoRepo;
        this.coordenadorRepo = coordenadorRepo;
    }

    public boolean cadastro(Aluno a) throws CadastroException {

        try {
            new ValidacaoMatricula().validar(a.getMatricula());
            new ValidacaoEmail().validar(a.getEmail());
            new ValidacaoSenha().validar(a.getSenha());

            new ValidacaoMatriculaExistente(alunoRepo).validar(a.getMatricula());
            new ValidacaoEmailExistente(alunoRepo, coordenadorRepo).validar(a.getEmail());

        } catch (ValidacaoException e) {

            throw new CadastroException(e.getMessage());
        }

        return alunoRepo.adicionarAluno(a);
    }


}