package Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.ValidacaoException;
import Interfaces.ValidacaoStrategy;

public class ValidacaoEmail implements ValidacaoStrategy<String> {
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

	@Override
	public void validar(String email) throws ValidacaoException {
		if (email == null) {
            throw new ValidacaoException("Email não pode ser nulo.");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        if (!matcher.matches()) {
            throw new ValidacaoException("Email fora de padrão.");
        }
	}
}

