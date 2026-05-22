package Interfaces;

import Model.Aluno;

import java.util.List;

public interface AlunoRepository {
    boolean adicionarAluno(Aluno aluno);
    List<Aluno> listarAlunos();
    Aluno recuperarAlunoPorMatricula(String nMatricula);
}
