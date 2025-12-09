package Projeto;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class TelaCadastroCoordenador extends BaseTelas {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private Cadastro cadastro;
    private CentralDeInformacoes central;
    private Persistencia p;

    public TelaCadastroCoordenador(CentralDeInformacoes c, Persistencia p) {
        // Define o tamanho da ÁREA DO FORMULÁRIO (o quadrado branco no meio)
        super("Cadastro de Coordenador", 450, 600);
        
		this.central = c;
		this.p = p;
        this.cadastro = new Cadastro(this.central);

        setTelaCheia(); 
    }

    @Override
    protected void montarTela() {
        JLabel lblTitulo = criarLabel("CADASTRO DE COODERNADOR", 0, 15, 450, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        estilizar(lblTitulo, 22, true);

        JLabel lblSecao1 = criarLabel("Dados", 30, 60, 200, 20);
        estilizar(lblSecao1, 12, true);
        lblSecao1.setForeground(java.awt.Color.GRAY);

        criarLabel("Email:", 30, 90, 80, 25);
        txtEmail = criarCampoTexto(100, 90, 280, 25);

        criarLabel("Senha:", 30, 130, 80, 25);
        txtSenha = criarCampoSenha(100, 130, 280, 25);

        criarLabel("Nome", 30, 170, 80, 25);
        txtNome = criarCampoTexto(100, 170, 280, 25);
        
        JButton btnSalvar = criarBotao("Salvar Cadastro", 50, 450, 160, 40, this::validarECadastrar);
        estilizar(btnSalvar, 14, true);
        btnSalvar.setForeground(new java.awt.Color(0, 100, 0));

        JButton btnCancelar = criarBotao("Cancelar", 230, 450, 150, 40, e -> dispose());
        estilizar(btnCancelar, 14, false);
    }
    
    private void validarECadastrar(ActionEvent e) {
        String senha = new String(txtSenha.getPassword());
        String email = txtEmail.getText();
        String nome = txtNome.getText();

        
        try {
        	
    		
        	Validacao.validacaoSenha(senha);
        	Validacao.emailExistente(email, central);
        	Validacao.isEmailValido(email);
        	cadastro.cadastrarCoordenador(senha, nome, email);
        	

        	
            JOptionPane.showMessageDialog(this, "Cadastro realizado!");
            this.salvar(e);


            this.dispose(); 

            new TelaLogin(central, p);

        }
        catch(CadastroException | ValidacaoException ex) {
        	JOptionPane.showMessageDialog(this, ex.getMessage());
        }

       
   
    }

    private void salvar(ActionEvent e) {
    	p.salvarCentral(central);
        JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
    }

}