package Model;

import Interfaces.Autenticavel;

public abstract class Pessoa implements Autenticavel {

	protected String nome;
	protected String email;
	protected String senha;

	// Construtor vazio (ou com parâmetros, dependendo de como você estruturou)
	public Pessoa() {
	}

	// --- MÉTODOS DA INTERFACE ---

	@Override
	public boolean autenticar(String senhaDigitada) {
		// A própria classe Pessoa sabe verificar se a senha digitada
		// é igual à senha que está salva nela mesma.
		if (this.senha == null || senhaDigitada == null) {
			return false;
		}
		return this.senha.equals(senhaDigitada);
	}

	// --- GETTERS E SETTERS ---

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	// Método abstrato que obriga as filhas a implementarem seu próprio toString()
	public abstract String toString();
}