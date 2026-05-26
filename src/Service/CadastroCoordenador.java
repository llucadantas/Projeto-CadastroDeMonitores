package Service;

import Exceptions.CadastroException;
import Exceptions.ValidacaoException;
import Interfaces.AlunoRepository;
import Interfaces.CadastroInterface;
import Model.Coordenador;
import Interfaces.CoordenadorRepository;

// O ideal seria que a interface CoordenadorRepository tivesse o método cadastrar.
// Como colocamos ele apenas na implementação (CoordenadorRepositoryImp),
// vamos instanciar a interface aqui, mas você precisa garantir que ela tenha o método.

public class CadastroCoordenador implements CadastroInterface<Coordenador> {

    private final CoordenadorRepository coordRepo;
    private final AlunoRepository alunoRepo;

    public CadastroCoordenador(CoordenadorRepository coordRepo, AlunoRepository alunoRepo) {
        this.coordRepo = coordRepo;
        this.alunoRepo = alunoRepo;
    }


    public boolean cadastro(Coordenador c) throws CadastroException {
    	// 1. Validação simples de preenchimento do nome
        if (c.getNome() == null || c.getNome().trim().isEmpty() || c.getSenha().trim().isEmpty() || c.getEmail().trim().isEmpty()) {
            throw new CadastroException("Todos os campos precisam ser preenchidos");
        }

        // 2. Verifica se já existe um coordenador (regra de negócio de coordenador único)
        if (coordRepo.getCoordenador() != null) {
            throw new CadastroException("Já existe um coordenador cadastrado no sistema.");
        }

        try {
            // 3. Executa as validações de formato básico usando o padrão Strategy
            new ValidacaoEmail().validar(c.getEmail());
            new ValidacaoSenha().validar(c.getSenha());

            // 4. Executa a validação se o e-mail já existe (seja de aluno ou de outro coordenador)
            new ValidacaoEmailExistente(alunoRepo, coordRepo).validar(c.getEmail());

        } catch (ValidacaoException e) {
            // Captura o erro da estratégia de validação e envelopa em CadastroException
            throw new CadastroException(e.getMessage());
        }

        // 5. Se passar por tudo com sucesso, cadastra o coordenador
        coordRepo.cadastrarCoordenador(c);
        return true;
    }

}