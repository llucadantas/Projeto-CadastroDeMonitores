package Service;

import Model.Aluno;
import Model.Coordenador;
import Repository.Interfaces.AlunoRepository;
// O ideal seria que a interface CoordenadorRepository tivesse o método cadastrar.
// Como colocamos ele apenas na implementação (CoordenadorRepositoryImp),
// vamos instanciar a interface aqui, mas você precisa garantir que ela tenha o método.
import Repository.CoordenadorRepositoryImp;

public class Cadastro {

    private final AlunoRepository alunoRepo;
    private final CoordenadorRepositoryImp coordRepo;

    public Cadastro(AlunoRepository alunoRepo, CoordenadorRepositoryImp coordRepo) {
        this.alunoRepo = alunoRepo;
        this.coordRepo = coordRepo;
    }

    public void cadastrarAluno(String matricula, String senha, String nome, String email) throws CadastroException {
        if (nome.trim().isEmpty() || matricula.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new CadastroException("Todos os campos precisam ser preenchidos");
        }

        // Substituindo a busca da central pela busca do repositório
        if (alunoRepo.recuperarAlunoPorMatricula(matricula.trim()) != null) {
            throw new CadastroException("Matrícula já cadastrada");
        }

        Aluno novoAluno = new Aluno(matricula, senha, nome, email);
        alunoRepo.adicionarAluno(novoAluno);
    }

    public void cadastrarCoordenador(String senha, String nome, String email) throws CadastroException {
        if (nome.trim().isEmpty() || senha.trim().isEmpty() || email.trim().isEmpty()) {
            throw new CadastroException("Todos os campos precisam ser preenchidos");
        }

        // Verifica se já existe um coordenador (regra de coordenador único)
        if (coordRepo.getCoordenador() != null) {
            throw new CadastroException("Já existe um coordenador cadastrado no sistema.");
        }

        Coordenador novoCoordenador = new Coordenador(senha, nome, email);
        coordRepo.cadastrarCoordenador(novoCoordenador);
    }

}