package Validation;

import Exceptions.ValidacaoException;
import Interfaces.ValidacaoStrategy;

public class ValidacaoMatricula implements ValidacaoStrategy<String> {

	@Override
	public void validar(String matricula) throws ValidacaoException {
		if (matricula == null || matricula.length() != 7) {
            throw new ValidacaoException("Matricula inválida");
        }
		
	}

}
