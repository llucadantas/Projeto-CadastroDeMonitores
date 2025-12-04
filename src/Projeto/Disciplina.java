package Projeto;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
	private String nome;
	private int nVagasRem;
	private int nVagasVol;
	private List<Aluno> alunos = new ArrayList<>();
	
	
	
	public Disciplina(String nome, int nVagasRem, int nVagasVol) {
		this.nome = nome;
		this.nVagasRem = nVagasRem;
		this.nVagasVol = nVagasVol;
	}


	public List<Aluno> getAlunos() {
		return alunos;
	}


	public int getnVagasRem() {
		return nVagasRem;
	}


	public void setnVagasRem(int nVagasRem) {
		this.nVagasRem = nVagasRem;
	}


	public int getnVagasVol() {
		return nVagasVol;
	}


	public void setnVagasVol(int nVagasVol) {
		this.nVagasVol = nVagasVol;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
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
