package Projeto;

import javax.swing.*;

import Projeto.BaseTelas;

import java.awt.event.ActionEvent;

public class TelaLogin extends BaseTelas {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private Login login;
    private Persistencia p;
    private CentralDeInformacoes c;

    public TelaLogin(CentralDeInformacoes c, Persistencia p) {
        super("Acesso Restrito", 350, 400);
        this.c = c;
        this.p = p;
        this.login = new Login(c);
        
        setTelaCheia();
    }

    @Override
    protected void montarTela() {
        
        JLabel lblTitulo = criarLabel("SISTEMA DE MONITORIA", 0, 20, 350, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        estilizar(lblTitulo, 20, true);

     
        JLabel lblUser = criarLabel("Usuário:", 45, 70, 80, 25);
        estilizar(lblUser, 12, true); 
        txtUsuario = criarCampoTexto(100, 70, 170, 25);

        JLabel lblSenha = criarLabel("Senha:", 45, 110, 80, 25);
        estilizar(lblSenha, 12, true); 
        txtSenha = criarCampoSenha(100, 110, 170, 25);

       
        JButton btnEntrar = criarBotao("Entrar", 45, 160, 110, 30, this::validarELogar);
        estilizar(btnEntrar, 11, true);

        
        JButton btnSair = criarBotao("Sair", 175, 160, 110, 30, e -> System.exit(0));
        estilizar(btnSair, 11, true);
       
       JButton btnCadastro=criarBotaoLink("Não possuo cadastro", 150, 215, 200, 20, this::telaCadastro);
       estilizar(btnCadastro, 12, true);
    }
    
    private void telaCadastro(ActionEvent e) {
    	this.dispose();
    	new TelaCadastroAluno(c, p);
    }

    
    private void validarELogar(ActionEvent e) {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        if (usuario.trim().isEmpty() || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            
            return;
        }
        
        if (login.loginCoodernador(usuario, senha)) {
            

            JOptionPane.showMessageDialog(this, "Login realizado como Coodernador! Abrindo sistema...");
            new TelaPrincipalCoordenador();


            this.dispose(); 

        } else if (login.login(usuario, senha)){
        	
            JOptionPane.showMessageDialog(this, "Login realizado como Coodernador! Abrindo sistema...");
        }
        else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    
    }

    public String getTxtUsuario() {
		return txtUsuario.getText();
	}

	public String getTxtSenha() {
		return String.valueOf(txtSenha.getPassword());
	}
	
	
}