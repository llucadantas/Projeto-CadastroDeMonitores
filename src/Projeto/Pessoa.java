package Projeto;

import java.util.UUID;

public abstract class Pessoa {
	public String nome;
	public String email;
	public String senha;
	

	public boolean isCoodernador;

	public String getNome() {
		return nome;
	}

	public boolean isCoodernador() {
		return isCoodernador;
	}

	public void setCoodernador(boolean isCoodernador) {
		this.isCoodernador = isCoodernador;
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
