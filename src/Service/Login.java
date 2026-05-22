package Service;

import Model.Aluno;
import Model.Coordenador;
import Repository.Interfaces.AlunoRepository;
import Repository.Interfaces.CoordenadorRepository;

public class Login {

	private final AlunoRepository alunoRepo;
	private final CoordenadorRepository coordRepo;

	// Guardam o estado do usuário logado
	private Aluno user;
	private Coordenador userCoordenador;

	// Agora o Login recebe os repositórios em vez da Central
	public Login(AlunoRepository alunoRepo, CoordenadorRepository coordRepo) {
		this.alunoRepo = alunoRepo;
		this.coordRepo = coordRepo;
	}

	public boolean login(String email, String senha) {
		// Percorre a lista de alunos para encontrar o e-mail correspondente
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

	// Getters para recuperar quem está logado após a validação
	public Aluno getUser() {
		return user;
	}

	public Coordenador getUserCoordenador() {
		return userCoordenador;
	}
}