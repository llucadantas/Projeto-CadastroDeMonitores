package Projeto;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class TelaCadastroCoodernador extends BaseTelas {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private Cadastro cadastro;
    private CentralDeInformacoes central;
    private Persistencia p;

    public TelaCadastroCoodernador(CentralDeInformacoes c, Persistencia p) {
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
        JLabel lblTitulo = criarLabel("CADASTRO DE COODERNADOR", 0, 15, 450, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        estilizar(lblTitulo, 22, true);

        // Seção 1
        JLabel lblSecao1 = criarLabel("Dados", 30, 60, 200, 20);
        estilizar(lblSecao1, 12, true);
        lblSecao1.setForeground(java.awt.Color.GRAY);

        criarLabel("CPF:", 30, 90, 80, 25);
        txtCpf = criarCampoTexto(100, 90, 280, 25);

        criarLabel("Senha:", 30, 130, 80, 25);
        txtSenha = criarCampoSenha(100, 130, 280, 25);

        criarLabel("Nome", 30, 170, 80, 25);
        txtNome = criarCampoTexto(100, 170, 280, 25);
        
        criarLabel("Email:", 30, 210, 80, 25);
        txtEmail = criarCampoTexto(100, 210, 280, 25);

        // Botões
        JButton btnSalvar = criarBotao("Salvar Cadastro", 50, 450, 160, 40, this::validarECadastrar);
        estilizar(btnSalvar, 14, true);
        btnSalvar.setForeground(new java.awt.Color(0, 100, 0));

        JButton btnCancelar = criarBotao("Cancelar", 230, 450, 150, 40, e -> dispose());
        estilizar(btnCancelar, 14, false);
    }
    
    private void validarECadastrar(ActionEvent e) {
        String usuario = txtCpf.getText();
        String senha = new String(txtSenha.getPassword());
        String email = txtEmail.getText();
        String nome = txtNome.getText();

        if (usuario.trim().isEmpty() || senha.trim().isEmpty() || nome.trim().isEmpty() || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        
        if (cadastro.cadastrarCoodernador(usuario, senha, nome, email).isEmpty()) {
            

            JOptionPane.showMessageDialog(this, "Cadastro realizado! Abrindo sistema...");
            this.salvar(e);


            this.dispose(); 

            new TelaLogin(central, p);
            
        } 
    }

    private void salvar(ActionEvent e) {
    	p.salvarCentral(central);
        JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
    }

}