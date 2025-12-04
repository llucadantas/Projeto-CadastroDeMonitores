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
	    
	    	    if (user != null) {
	        
	        if (user.getSenha().equals(senha) && user.getMatricula().equals(matricula)) { 
	            return true; 
	        }
	    }

	    return false;
	}
	
	public boolean loginCoodernador(long id, String senha) {
		userCoodernador = central.getCoodernador();
		
		if(userCoodernador != null && userCoodernador.getUsuario() == id) {
			if(userCoodernador.getSenha().equals(senha)) {
				return true;
			}
		}
		
		return false;
	}

}
