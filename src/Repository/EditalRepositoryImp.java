package Repository;

import Model.Aluno;
import Model.Disciplina;
import Model.EditalDeMonitoria;
import Interfaces.EditalRepository;

import java.util.ArrayList;
import java.util.List;

public class EditalRepositoryImp implements EditalRepository {

    private List<EditalDeMonitoria> editais;
    private final Persistencia persistencia;
    private static final String ARQUIVO = "editais.xml";

    public EditalRepositoryImp() {
        this.persistencia = Persistencia.getInstance();
        this.editais = persistencia.recuperarDados(ARQUIVO);
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

            // Olha as disciplinas dentro do edital
            for (Disciplina disciplina : edital.getDisciplinas()) {

                // Olha os alunos dentro da disciplina
                for (Aluno a : disciplina.getAlunos()) {
                    if (aluno.getMatricula().equals(a.getMatricula())) {
                        editaisDoAluno.add(edital);
                        encontrouAluno = true;
                        break; // Já achou o aluno nesta disciplina, pode parar a busca de alunos
                    }
                }

                // Se já achou o aluno no edital, não precisa olhar as outras disciplinas dele
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
            persistencia.salvarDados(editais, ARQUIVO);
        }

        return sucesso;
    }

    // Método auxiliar para salvar caso você atualize algum dado do edital em memória (ex: inscrever um aluno)
    public void atualizarDados() {
        persistencia.salvarDados(editais, ARQUIVO);
    }
}