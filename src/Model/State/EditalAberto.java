package Model.State;

import Interfaces.EstadoEdital;
import Model.Aluno;
import Model.Disciplina;
import Model.EditalDeMonitoria;

public class EditalAberto implements EstadoEdital {

    @Override
    public boolean permitirInscricao(EditalDeMonitoria edital, Aluno aluno, String disciplina) {
        for(Disciplina d : edital.getDisciplinas()) {
            if(d.getNome().equalsIgnoreCase(disciplina)) {
                return d.adicionarAluno(aluno);
            }
        }

        return false;
    }

    @Override
    public String getNomeEstado() {
        return "Aberto";
    }


}
