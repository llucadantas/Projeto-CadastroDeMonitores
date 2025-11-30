package Projeto;

public class Cadastro {
	private CentralDeInformacoes central;
	
	public Cadastro(CentralDeInformacoes central) {
        this.central = central;
    }


    public String cadastrarAluno(String matricula, String senha, String nome, String email) {
        
        if (nome.trim().isEmpty() || matricula.trim().isEmpty() || senha.trim().isEmpty()) {
            return "Todos os campos obrigatórios devem ser preenchidos.";
        }
        
        if (central.recuperarAlunoPorMatricula(matricula) != null) {
            return "Matrícula já cadastrada no sistema.";
        }
        Aluno novoAluno = new Aluno(nome, email, senha, matricula);
        
        central.adicionarAluno(novoAluno);
        
        
        return ""; 
    }
    
    public String cadastrarCoodernador(String cpf, String senha, String nome,String email) {
        
        if (nome.trim().isEmpty() || cpf.trim().isEmpty() || senha.trim().isEmpty()) {
            return "Todos os campos obrigatórios devem ser preenchidos.";
        }

        
        Coodernador novoCoodernador = new Coodernador(cpf, senha, nome, email);
        
        this.central.setCoodernador(novoCoodernador);
        
        
        return "";
    }
    
}


