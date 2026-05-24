package Interfaces;

import Model.Aluno;
import Model.EditalDeMonitoria;

public interface EstadoEdital {

    boolean permitirInscricao(EditalDeMonitoria edital, Aluno aluno, String disciplina);

    String getNomeEstado();
}
