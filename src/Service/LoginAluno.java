package Service;

import Interfaces.LoginInterface;
import Interfaces.AlunoRepository;
import Model.Aluno;
import Model.Pessoa;

public class LoginAluno implements LoginInterface {

	private final AlunoRepository alunoRepo;
	private Aluno usuarioLogado;

	public LoginAluno(AlunoRepository alunoRepo) {
		this.alunoRepo = alunoRepo;
	}

	@Override
	public boolean logar(String email, String senha) {
		// Varre a lista de alunos
		for (Aluno a : alunoRepo.listarAlunos()) {
			if (a.getEmail().equals(email)) {
				// Utiliza a interface Autenticavel (implementada em Aluno/Pessoa)
				if (a.autenticar(senha)) {
					this.usuarioLogado = a;
					return true;
				}
			}
		}

		this.usuarioLogado = null;
		return false;
	}

	@Override
	public Pessoa getUser() {
		return this.usuarioLogado;
	}
}