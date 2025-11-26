package Projeto;

public class Aluno {
	private String nome;
	private SexoLista sexo;
	private String matricula;
	private String email;
	private String senha;
	
	public Aluno(String nome, SexoLista sexo, String matricula, String email, String senha) {
		this.nome = nome;
		this.sexo = sexo;
		this.matricula = matricula;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SexoLista getSexo() {
		return sexo;
	}

	public void setSexo(SexoLista sexo) {
		this.sexo = sexo;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String toString() {
		return this.nome;
	}
	
	
}
