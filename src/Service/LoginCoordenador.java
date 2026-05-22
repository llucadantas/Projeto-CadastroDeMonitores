package Service;

import Interfaces.LoginInterface;
import Model.Aluno;
import Model.Coordenador;
import Interfaces.CoordenadorRepository;
import Model.Pessoa;

public class LoginCoordenador implements LoginInterface {

	private final CoordenadorRepository coordRepo;

	private Aluno user;
	private Coordenador userCoordenador;

	public LoginCoordenador(CoordenadorRepository coordRepo) {
		this.coordRepo = coordRepo;
	}

	public boolean logar(String email, String senha) {
		userCoordenador = coordRepo.getCoordenador();

		if (userCoordenador != null && userCoordenador.getEmail().equals(email)) {
			if (userCoordenador.getSenha().equals(senha)) {
				return true;
			}
		}
		return false;
	}

	public Pessoa getUser() {
		return userCoordenador;
	}
}