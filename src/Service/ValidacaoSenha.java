package Service;

import Exceptions.ValidacaoException;
import Interfaces.ValidacaoStrategy;

public class ValidacaoSenha implements ValidacaoStrategy<String> {

	@Override
	public void validar(String senha) throws ValidacaoException {
		if (senha == null || senha.length() < 7) {
            throw new ValidacaoException("Senha menor que 7 caracteres.");
        }
		
	}

}
