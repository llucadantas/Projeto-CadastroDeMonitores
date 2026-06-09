package Repository;

import Model.Disciplina;
import Interfaces.DisciplinaRepository;

import java.util.List;

public class DisciplinaRepositoryImp implements DisciplinaRepository {

    private List<Disciplina> disciplinas;
    private final PersistenciaSingleton persistenciaSingleton;
    private static final String ARQUIVO = "disciplinas.xml";

    public DisciplinaRepositoryImp() {
        this.persistenciaSingleton = PersistenciaSingleton.getInstance();
        this.disciplinas = persistenciaSingleton.recuperarDados(ARQUIVO);
    }

    @Override
    public boolean cadastrarDisciplina(Disciplina disciplina) {
        if (buscarDisciplina(disciplina.getNome()) != null) {
            return false;
        }

        boolean sucesso = disciplinas.add(disciplina);

        if (sucesso) {
            persistenciaSingleton.salvarDados(disciplinas, ARQUIVO);
        }

        return sucesso;
    }

    @Override
    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    @Override
    public Disciplina buscarDisciplina(String nome) {
        for (Disciplina d : disciplinas) {
            if (d.getNome().equalsIgnoreCase(nome)) {
                return d;
            }
        }
        return null;
    }

    public void atualizarDados() {
        persistenciaSingleton.salvarDados(disciplinas, ARQUIVO);
    }
}