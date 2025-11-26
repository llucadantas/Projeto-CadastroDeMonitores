package Projeto;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
	private String nome;
	private int nVagas;
	private List<Aluno> alunos = new ArrayList<>();
	
	
	
	public Disciplina(String nome, int nVagas) {
		
		this.nome = nome;
		this.nVagas = nVagas;
	}


	public List<Aluno> getAlunos() {
		return alunos;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getnVagas() {
		return nVagas;
	}


	public void setnVagas(int nVagas) {
		this.nVagas = nVagas;
	}


	public boolean adicionarAluno(Aluno aluno) {
		for(Aluno aluno1: alunos) {
			if(aluno1.getMatricula().equals(aluno.getMatricula())) {
				
				return false;
			}
		}
		return alunos.add(aluno);
	}
	
	
	
	

}
