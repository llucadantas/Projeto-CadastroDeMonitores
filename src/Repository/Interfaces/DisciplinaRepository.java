package Repository.Interfaces;

import Model.Disciplina;
import java.util.List;

public interface DisciplinaRepository {
    boolean cadastrarDisciplina(Disciplina disciplina);
    List<Disciplina> listarDisciplinas();
    Disciplina buscarDisciplina(String nome);
}