package Projeto;

public class Aluno extends Pessoa{
	

	public String matricula;

	
	public Aluno(String matricula, String senha, String nome, String email) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.email = email;
		this.senha = senha;
		this.isCoodernador = false;
	}


	public String getMatricula() {
		return matricula;
	}
	


	public String toString() {
		return nome;
	}

	
}

