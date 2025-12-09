package Projeto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacao {
	

	
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
	
	public static void validacaoSenha(String senha) throws ValidacaoException {
		if(senha.length() < 7 ) {
			throw new ValidacaoException("Senha menor que 7 caracteres.");
		}
	
	}
	
	public static void senhaIgual(String s1, String s2) throws ValidacaoException {
		if(!s1.equals(s2)) {
			throw new ValidacaoException("As duas senhas nao conhecidem.");
		}
	}
	
	public static void nome(String n) throws ValidacaoException{
		if(n.isEmpty()) {
			throw new ValidacaoException("Digite seu nome.");
		}
	}
	
	public static void matriculaExistente(String matricula, CentralDeInformacoes central) throws ValidacaoException{
		Aluno a = null;
		a = central.recuperarAlunoPorMatricula(matricula);
		if(!(a == null)) {
			throw new ValidacaoException("Matricula já existente;");
		}
	}
	
	public static void matriculaInvalida(String matricula) throws ValidacaoException {
		if(matricula.length() != 7) {
			throw new ValidacaoException("Matricula inválida");

		}
	}
	
	public static void emailExistente(String email, CentralDeInformacoes central) throws ValidacaoException {
	    for (Aluno a : central.getTodosAlunos()) {
	        if (a.getEmail().equals(email)) {
	            throw new ValidacaoException("Email já existente.");
	        }
	    }

	    Coordenador c = central.getCoordenador();

	    if (c != null && c.getEmail().equals(email)) {
	        throw new ValidacaoException("Email já existente.");
	    }
	}
	
}
