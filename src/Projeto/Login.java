package Projeto;

public class Login {
	
	private CentralDeInformacoes central;
	private Aluno user;
	private Coodernador userCoodernador;
	
	public Login(CentralDeInformacoes c) {
		this.central = c;
	}
	
	public boolean login(String matricula, String senha) {
	    
	    user = central.recuperarAlunoPorMatricula(matricula);
	    
	    
	    // 2. Verifica se o aluno existe
	    if (user != null) {
	        
	        // 3. Verifica a senha (Ainda precisa ser com HASH!)
	        if (user.getSenha().equals(senha)) { 
	            return true; 
	        }
	    }

	    return false;
	}
	
	public boolean loginCoodernador(String cpf, String senha) {
		userCoodernador = central.getCoodernador();
		
		if(userCoodernador != null || userCoodernador.getCpf().equals(cpf)) {
			if(userCoodernador.getSenha().equals(senha)) {
				return true;
			}
		}
		
		return false;
	}

}
