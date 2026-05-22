package Model;

public class Coordenador extends Pessoa {
	
	
	public Coordenador(String senha, String nome, String email) {
		super();
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
	}

	public String toString() {
		return "";
	}
}
