package Projeto;

public class Coodernador extends Pessoa {
	
	private String cpf;
	
	public Coodernador(String cpf,String senha, String nome, String email) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.isCoodernador = true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getCpf(){
		return cpf;
	}
	
	

}
