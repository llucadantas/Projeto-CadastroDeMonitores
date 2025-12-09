package Projeto;

public class Coordenador extends Pessoa {
	
	
	public Coordenador(String senha, String nome, String email) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.isCoodernador = true;
	}

	@Override
	public String toString() {
		return "";
	}

	
	

}
