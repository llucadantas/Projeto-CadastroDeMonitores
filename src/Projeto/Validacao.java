package Projeto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacao {
	
	public static void validacaoSenha(String senha) throws ValidacaoException {
		if(senha.length() < 7 ) {
			throw new ValidacaoException("Senha menor que 7 caracteres.");
		}
	
	}
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	

	public static void isEmailValido(String email) throws ValidacaoException {

		// Cria um Matcher para comparar o e-mail com o padrão
        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        
        // Retorna o resultado da comparação
        if(!matcher.matches()) {
        	throw new ValidacaoException("Email fora de padrão.");
        }
    }
	
}
