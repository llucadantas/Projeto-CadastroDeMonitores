package Model;

import Interfaces.Autenticavel;

public abstract class Pessoa implements Autenticavel {

	protected String nome;
	protected String email;
	protected String senha;

	public Pessoa() {
	}


	@Override
	public boolean autenticar(String senhaDigitada) {
		if (this.senha == null || senhaDigitada == null) {
			return false;
		}
		return this.senha.equals(senhaDigitada);
	}


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

	public abstract String toString();
}