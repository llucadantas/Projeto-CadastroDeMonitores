package Service;

import Interfaces.LoginInterface;
import Interfaces.CoordenadorRepository;
import Model.Coordenador;
import Model.Pessoa;

public class LoginCoordenador implements LoginInterface {

	private final CoordenadorRepository coordRepo;
	private Coordenador usuarioLogado;

	public LoginCoordenador(CoordenadorRepository coordRepo) {
		this.coordRepo = coordRepo;
	}

	@Override
	public boolean logar(String email, String senha) {
		Coordenador coord = coordRepo.getCoordenador();

		if (coord != null && coord.getEmail().equals(email)) {
			if (coord.autenticar(senha)) {
				this.usuarioLogado = coord;
				return true;
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