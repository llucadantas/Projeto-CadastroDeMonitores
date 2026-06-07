package Interfaces;

public interface Autenticavel {
    
    /**
     * Valida se a senha fornecida pelo usuário corresponde à senha gravada.
     * @param senha A senha digitada na tela de login.
     * @return true se a senha estiver correta, false caso contrário.
     */
    boolean autenticar(String senha);

    /**
     * Recupera o e-mail do usuário para fins de busca e identificação no login.
     * @return O e-mail cadastrado.
     */
    String getEmail();
}