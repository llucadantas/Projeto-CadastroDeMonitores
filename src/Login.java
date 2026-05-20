public class Login {
	
	private CentralDeInformacoes central;
	private Aluno user;
	private Coordenador userCoordenador;
	
	public Login(CentralDeInformacoes c) {
		this.central = c;
	}
	
	public boolean login(String email, String senha) {
		Aluno a = central.recuperarAlunoPorEmail(email);
		    if(!(a==null)) {
		        if (a.getEmail().equals(email) && a.getSenha().equals(senha)) {
		            this.user = a;
		            return true;
		        
		    }
		        return false;
		    }
		    return false;
		    }
		    
		    

	
	public boolean loginCoodernador(String email, String senha) {
		userCoordenador = central.getCoordenador();
		
		if(userCoordenador != null && userCoordenador.getEmail().equals(email)) {
			if(userCoordenador.getSenha().equals(senha)) {
				return true;
			}
		}
		
		return false;
	}

}
