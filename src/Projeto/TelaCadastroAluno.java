package Projeto;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class TelaCadastroAluno extends BaseTelas {

    private JTextField txtNome;
    private JTextField txtMatricula;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private Cadastro cadastro;
    private CentralDeInformacoes central;
    private Persistencia p;

    public TelaCadastroAluno(CentralDeInformacoes c, Persistencia p) {
        // Define o tamanho da ÁREA DO FORMULÁRIO (o quadrado branco no meio)
        super("Cadastro de Coodernador", 450, 600);
        
		this.central = c;
		this.p = p;
        this.cadastro = new Cadastro(c);

        // COMANDO MÁGICO: Transforma em tela cheia
        setTelaCheia(); 
    }

    @Override
    protected void montarTela() {
        // Título
        JLabel lblTitulo = criarLabel("CADASTRO DE ALUNO", 0, 15, 450, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        estilizar(lblTitulo, 22, true);

        // Seção 1
        JLabel lblSecao1 = criarLabel("Dados", 30, 60, 200, 20);
        estilizar(lblSecao1, 12, true);
        lblSecao1.setForeground(java.awt.Color.GRAY);

        criarLabel("Matricula:", 30, 90, 80, 25);
        txtMatricula = criarCampoTexto(100, 90, 280, 25);

        criarLabel("Senha:", 30, 130, 80, 25);
        txtSenha = criarCampoSenha(100, 130, 280, 25);

        criarLabel("Nome:", 30, 170, 80, 25);
        txtNome = criarCampoTexto(100, 170, 280, 25);
        
        criarLabel("Email:", 30, 210, 80, 25);
        txtEmail = criarCampoTexto(100, 210, 280, 25);

        // Botões
        JButton btnSalvar = criarBotao("Salvar Cadastro", 50, 450, 160, 40, this::validarECadastrar);
        estilizar(btnSalvar, 14, true);
        btnSalvar.setForeground(new java.awt.Color(0, 100, 0));

        JButton btnCancelar = criarBotao("Voltar", 230, 450, 150, 40, this::telaLogin);
        estilizar(btnCancelar, 14, false);
        
    }
    
    public void telaLogin(ActionEvent e) {
    	this.dispose();
    	new TelaLogin(central, p);
    	
    }
    
    private void validarECadastrar(ActionEvent e) {
        String matricula = txtMatricula.getText();
        String senha = new String(txtSenha.getPassword());
        String email = txtEmail.getText();
        String nome = txtNome.getText();

        
        try {
        	
        	Validacao.validacaoSenha(senha);
        	Validacao.isEmailValido(email);
        	cadastro.cadastrarAluno(matricula, senha, nome, email);

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