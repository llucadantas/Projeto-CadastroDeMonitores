package Service;

import Interfaces.LoginInterface;
import Model.Aluno;
import Interfaces.AlunoRepository;
import Model.Pessoa;

public class LoginAluno implements LoginInterface {

	private final AlunoRepository alunoRepo;

	private Aluno user;

	public LoginAluno(AlunoRepository alunoRepo) {
		this.alunoRepo = alunoRepo;
	}

	public boolean logar(String email, String senha) {
		for (Aluno a : alunoRepo.listarAlunos()) {
			if (a.getEmail().equals(email) && a.getSenha().equals(senha)) {
				this.user = a;
				return true;
			}
		}
		return false;
	}

	public Pessoa getUser() {
		return user;
	}


}