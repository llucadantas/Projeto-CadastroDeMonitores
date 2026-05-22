package Repository;

import Model.Aluno;
import Interfaces.AlunoRepository;

import java.util.List;

public class AlunoRepositoryImp implements AlunoRepository {

    private List<Aluno> alunos;
    private final Persistencia persistencia;
    private static final String ARQUIVO = "alunos.xml";

    // O construtor agora inicializa a persistência e carrega a lista do arquivo
    public AlunoRepositoryImp() {
        this.persistencia = Persistencia.getInstance();
        this.alunos = persistencia.recuperarDados(ARQUIVO);
    }

    @Override
    public boolean adicionarAluno(Aluno aluno) {
        // Regra de negócio opcional, mas recomendada: evitar matrículas duplicadas
        if (recuperarAlunoPorMatricula(aluno.getMatricula()) != null) {
            return false;
        }

        boolean sucesso = alunos.add(aluno);

        // Se o aluno foi adicionado com sucesso na memória, salvamos no XML
        if (sucesso) {
            persistencia.salvarDados(alunos, ARQUIVO);
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
            // Usando a comparação segura que discutimos antes para evitar NullPointerException
            if (nMatricula.equals(a.getMatricula())) {
                return a;
            }
        }
        return null;
    }

    // Método auxiliar (opcional) para uso interno caso precise forçar um salvamento depois de atualizar um aluno
    public void atualizarDados() {
        persistencia.salvarDados(alunos, ARQUIVO);
    }
}