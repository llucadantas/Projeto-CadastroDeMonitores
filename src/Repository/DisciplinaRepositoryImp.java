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
        // Verifica se já existe uma disciplina com o mesmo nome antes de adicionar
        if (buscarDisciplina(disciplina.getNome()) != null) {
            return false;
        }

        boolean sucesso = disciplinas.add(disciplina);

        // Salva automaticamente no XML
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
            // Compara os nomes ignorando diferenças entre maiúsculas e minúsculas
            if (d.getNome().equalsIgnoreCase(nome)) {
                return d;
            }
        }
        return null;
    }

    // Método auxiliar caso precise atualizar algo (como a quantidade de vagas) e salvar
    public void atualizarDados() {
        persistenciaSingleton.salvarDados(disciplinas, ARQUIVO);
    }
}