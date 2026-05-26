package Interfaces;

import Exceptions.ValidacaoException;

public interface ValidacaoStrategy<T> {
	
	void validar(T valor) throws ValidacaoException;
	
}
