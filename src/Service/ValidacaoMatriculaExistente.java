package Service;

import Exceptions.ValidacaoException;
import Interfaces.AlunoRepository;
import Interfaces.ValidacaoStrategy;
import Model.Aluno;

public class ValidacaoMatriculaExistente implements ValidacaoStrategy<String> {
	private final AlunoRepository alunoRepository;
	
	public ValidacaoMatriculaExistente(AlunoRepository alunoRepository) {
		this.alunoRepository = alunoRepository;
	}
	@Override
	public void validar(String matricula) throws ValidacaoException {
		if (alunoRepository != null) {
            Aluno aluno = alunoRepository.recuperarAlunoPorMatricula(matricula); 
            if (aluno != null) {
                throw new ValidacaoException("Matricula já existente;");
            }
        }
		
	}

}
