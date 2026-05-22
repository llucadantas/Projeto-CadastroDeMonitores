package Service;

import Model.Aluno;
import Model.Coordenador;
import Repository.Interfaces.AlunoRepository;
import Repository.Interfaces.CoordenadorRepository;

public class Login {

	private final AlunoRepository alunoRepo;
	private final CoordenadorRepository coordRepo;

	private Aluno user;
	private Coordenador userCoordenador;

	public Login(AlunoRepository alunoRepo, CoordenadorRepository coordRepo) {
		this.alunoRepo = alunoRepo;
		this.coordRepo = coordRepo;
	}

	public boolean login(String email, String senha) {
		for (Aluno a : alunoRepo.listarAlunos()) {
			if (a.getEmail().equals(email) && a.getSenha().equals(senha)) {
				this.user = a;
				return true;
			}
		}
		return false;
	}

	public boolean loginCoodernador(String email, String senha) {
		userCoordenador = coordRepo.getCoordenador();

		if (userCoordenador != null && userCoordenador.getEmail().equals(email)) {
			if (userCoordenador.getSenha().equals(senha)) {
				return true;
			}
		}
		return false;
	}

	public Aluno getUser() {
		return user;
	}

	public Coordenador getUserCoordenador() {
		return userCoordenador;
	}
}