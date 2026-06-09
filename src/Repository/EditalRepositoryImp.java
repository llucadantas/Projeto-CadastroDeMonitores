package Repository;

import Model.Aluno;
import Model.Disciplina;
import Model.EditalDeMonitoria;
import Interfaces.EditalRepository;

import java.util.ArrayList;
import java.util.List;

public class EditalRepositoryImp implements EditalRepository {

    private List<EditalDeMonitoria> editais;
    private final PersistenciaSingleton persistenciaSingleton;
    private static final String ARQUIVO = "editais.xml";

    public EditalRepositoryImp() {
        this.persistenciaSingleton = PersistenciaSingleton.getInstance();
        this.editais = persistenciaSingleton.recuperarDados(ARQUIVO);
    }

    @Override
    public List<EditalDeMonitoria> listarEditais() {
        return editais;
    }

    @Override
    public EditalDeMonitoria buscarEdital(long id) {
        for (EditalDeMonitoria e : editais) {
            if (id == e.getId()) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<EditalDeMonitoria> EditaisAluno(Aluno aluno) {
        List<EditalDeMonitoria> editaisDoAluno = new ArrayList<>();

        // Percorre todos os editais salvos
        for (EditalDeMonitoria edital : editais) {
            boolean encontrouAluno = false;

            for (Disciplina disciplina : edital.getDisciplinas()) {

                // Olha os alunos dentro da disciplina
                for (Aluno a : disciplina.getAlunos()) {
                    if (aluno.getMatricula().equals(a.getMatricula())) {
                        editaisDoAluno.add(edital);
                        encontrouAluno = true;
                        break;
                    }
                }

                if (encontrouAluno) {
                    break;
                }
            }
        }

        return editaisDoAluno;
    }

    @Override
    public boolean cadastrarEdital(EditalDeMonitoria edital) {
        if (buscarEdital(edital.getId()) != null) {
            return false;
        }

        boolean sucesso = editais.add(edital);

        if (sucesso) {
            persistenciaSingleton.salvarDados(editais, ARQUIVO);
        }

        return sucesso;
    }

    public void atualizarDados() {
        persistenciaSingleton.salvarDados(editais, ARQUIVO);
    }
}