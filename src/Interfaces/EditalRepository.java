package Interfaces;

import Model.Aluno;
import Model.EditalDeMonitoria;

import java.util.List;

public interface EditalRepository {
    List<EditalDeMonitoria> listarEditais();
    EditalDeMonitoria buscarEdital(long id);
    List<EditalDeMonitoria> EditaisAluno(Aluno aluno);
    boolean cadastrarEdital(EditalDeMonitoria edital);

}
