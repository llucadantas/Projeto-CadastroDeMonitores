package Service;

import Exceptions.ValidacaoException;
import Interfaces.AlunoRepository;
import Interfaces.CoordenadorRepository;
import Interfaces.ValidacaoStrategy;
import Model.Aluno;
import Model.Coordenador;

public class ValidacaoEmailExistente implements ValidacaoStrategy<String> {
	private final AlunoRepository alunoRepository;
	private final CoordenadorRepository coordenadorRepository;
	
	public ValidacaoEmailExistente(AlunoRepository alunoRepository, CoordenadorRepository coordenadorRepository) {
		this.alunoRepository = alunoRepository;
		this.coordenadorRepository = coordenadorRepository;
	}
	@Override
	public void validar(String email) throws ValidacaoException {
		if (alunoRepository != null && alunoRepository.listarAlunos() != null) {
            for (Aluno a : alunoRepository.listarAlunos()) {
                if (a.getEmail().equals(email)) {
                    throw new ValidacaoException("Email já existente.");
                }
            }
		}
		
		if (coordenadorRepository != null) {
            Coordenador c = coordenadorRepository.getCoordenador();
            if (c != null && c.getEmail().equals(email)) {
                throw new ValidacaoException("Email já existente.");
            }
        }
	}

}
