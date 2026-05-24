package Model.State;

import Interfaces.EstadoEdital;
import Model.Aluno;
import Model.EditalDeMonitoria;

public class EditalAguardando implements EstadoEdital {

    @Override
    public boolean permitirInscricao(EditalDeMonitoria edital, Aluno aluno, String disciplina) {
        return false;
    }

    @Override
    public String getNomeEstado() {
        return "Aguardando Abertura";
    }
}
