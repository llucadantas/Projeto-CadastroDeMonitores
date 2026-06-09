package Repository;

import Model.Aluno;
import Interfaces.AlunoRepository;

import java.util.List;

public class AlunoRepositoryImp implements AlunoRepository {

    private List<Aluno> alunos;
    private final PersistenciaSingleton persistenciaSingleton;
    private static final String ARQUIVO = "alunos.xml";

    public AlunoRepositoryImp() {
        this.persistenciaSingleton = PersistenciaSingleton.getInstance();
        this.alunos = persistenciaSingleton.recuperarDados(ARQUIVO);
    }

    @Override
    public boolean adicionarAluno(Aluno aluno) {
        if (recuperarAlunoPorMatricula(aluno.getMatricula()) != null) {
            return false;
        }

        boolean sucesso = alunos.add(aluno);

        if (sucesso) {
            persistenciaSingleton.salvarDados(alunos, ARQUIVO);
        }

        return sucesso;
    }

    @Override
    public List<Aluno> listarAlunos() {
        return alunos;
    }

    @Override
    public Aluno recuperarAlunoPorMatricula(String nMatricula) {
        for (Aluno a : alunos) {
            if (nMatricula.equals(a.getMatricula())) {
                return a;
            }
        }
        return null;
    }

    public void atualizarDados() {
        persistenciaSingleton.salvarDados(alunos, ARQUIVO);
    }
}