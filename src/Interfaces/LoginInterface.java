package Interfaces;

import Model.Pessoa;

public interface LoginInterface {
    boolean logar(String email, String senha);
    Pessoa getUser();
}
