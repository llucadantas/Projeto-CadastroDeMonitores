package Interfaces;

import Model.Pessoa;
import Exceptions.CadastroException;

public interface CadastroInterface<T extends Pessoa> {
    boolean cadastro(T pessoa) throws CadastroException;
}
