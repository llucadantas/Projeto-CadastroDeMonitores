package Projeto;

public class Cadastro {
	private CentralDeInformacoes central;
	
	public Cadastro(CentralDeInformacoes central) {
        this.central = central;
    }


    public void cadastrarAluno(String matricula, String senha, String nome, String email) throws CadastroException {
    	
    	
        
        if (nome.trim().isEmpty() || matricula.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new CadastroException("Todos os campos precisam ser preenchidos");
        }
        
        if (central.recuperarAlunoPorMatricula(matricula.trim())!= null) {
            throw new CadastroException("Matricula já cadastrada");
        }
        Aluno novoAluno = new Aluno(matricula, senha, nome, email );
        
        central.adicionarAluno(novoAluno);
        
           }
    
    public void cadastrarCoordenador(String senha, String nome,String email) throws CadastroException {
        
        if (nome.trim().isEmpty() ||senha.trim().isEmpty() || email.trim().isEmpty()) {
            throw new CadastroException("Todos os campos precisam ser preenchidos");
        }

        
        Coordenador novoCoodernador = new Coordenador(senha, nome, email);
        
        this.central.setCoodernador(novoCoodernador);
        
    }
    
}


