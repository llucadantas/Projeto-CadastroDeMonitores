package Model;

public class Aluno extends Pessoa {
	

	public String matricula;

	public Aluno(String matricula, String senha, String nome, String email) {
		super();
		this.setNome(nome);
		this.matricula = matricula;
		this.setEmail(email);
		this.setSenha(senha);
	}


	public String getMatricula() {
		return matricula;
	}
	


	public String toString() {
		return this.getNome();
	}

	
}

