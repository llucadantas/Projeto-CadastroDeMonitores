package Projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Perfil extends BaseTelas {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtLogin;
    private JPasswordField txtNovaSenha;
    private JPasswordField txtConfirmaSenha;
    private CentralDeInformacoes c;
    private Pessoa pessoa;
    private String matricula;
    private Persistencia p;
    
    public Perfil(CentralDeInformacoes central, boolean isCoordenador, String m, Persistencia p) {
        super("Meu Perfil", 500, 520);
        // DISPOSE_ON_CLOSE é vital aqui: fecha só esta janela, não o programa todo
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.c = central;
        this.matricula = m;
        this.p = p;
        
        if(isCoordenador) {
            this.pessoa = c.getCoodernador();
        }
        else {
            this.pessoa = c.recuperarAlunoPorMatricula(matricula);
        }
        
        if (this.pessoa != null) {
            montarTela();
        }
    }

    @Override
    protected void montarTela() {
    	
    	if (this.pessoa == null) {
            return;
        }

        // --- SEÇÃO 1: DADOS PESSOAIS ---
        JLabel lblDados = criarLabel("Dados Pessoais", 30, 20, 200, 30);
        estilizar(lblDados, 16, true);
        lblDados.setForeground(new Color(0, 102, 204));

        criarLabel("Nome Completo:", 30, 60, 150, 20);
        txtNome = criarCampoTexto(30, 80, 420, 30);
        txtNome.setText(pessoa.getNome()); 
        
        if(pessoa.isCoodernador()) {
            criarLabel("Login (Imutável):", 30, 180, 150, 20);
            txtLogin = criarCampoTexto(30, 200, 200, 30);
            txtLogin.setText(pessoa.getEmail());
            txtLogin.setEditable(false);
            txtLogin.setBackground(new Color(230, 230, 230));
            txtLogin.setForeground(Color.GRAY);
        }
        else {
            criarLabel("E-mail Institucional:", 30, 120, 150, 20);
            txtEmail = criarCampoTexto(30, 140, 420, 30);
            txtEmail.setText(pessoa.getEmail());
            criarLabel("Login (Imutável):", 30, 180, 150, 20);
            txtLogin = criarCampoTexto(30, 200, 200, 30);
            txtLogin.setText(matricula);
            
        }

        JSeparator sep = new JSeparator();
        sep.setBounds(30, 250, 420, 2);
        painel.add(sep);

      
        JLabel lblSeguranca = criarLabel("Alterar Senha", 30, 270, 200, 30);
        estilizar(lblSeguranca, 16, true);
        lblSeguranca.setForeground(new Color(0, 102, 204));

        criarLabel("Nova Senha:", 30, 310, 150, 20);
        txtNovaSenha = criarCampoSenha(30, 330, 200, 30);
        

        criarLabel("Confirmar Senha:", 250, 310, 150, 20);
        txtConfirmaSenha = criarCampoSenha(250, 330, 200, 30);

        JButton btnSalvar = criarBotao("Salvar Alterações", 30, 410, 200, 40, null); 
        // Adicionando o Listener manualmente para ficar claro
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarSalvar();
            }
        });
        btnSalvar.setBackground(new Color(0, 153, 76)); 
        btnSalvar.setForeground(Color.BLACK);

        // 2. Botão Cancelar com Listener de Fechamento
        JButton btnCancelar = criarBotao("Cancelar", 250, 410, 200, 40, e -> {
            // Apenas fecha a janela sem fazer nada
            dispose(); 
        });
        btnCancelar.setBackground(new Color(200, 50, 50)); 
        btnCancelar.setForeground(Color.BLACK);
    }

    /**
     * Método contendo a lógica de negócio do botão Salvar.
     * Separá-lo do listener deixa o código mais limpo (Clean Code).
     */
    private void executarSalvar() {

        // 1. Captura os dados
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String s1 = new String(txtNovaSenha.getPassword());
        String s2 = new String(txtConfirmaSenha.getPassword());

        try {
            Validacao.isEmailValido(email);
            Validacao.nome(nome);
            if (!s1.isEmpty() || !s2.isEmpty()) {
                Validacao.senhaIgual(s1, s2);
                Validacao.validacaoSenha(s1);
                pessoa.setSenha(s1);
                }

            pessoa.setEmail(email);
            pessoa.setNome(nome);
            
            
            p.salvarCentral(c);
            
            JOptionPane.showConfirmDialog(this, "Atualizado com sucesso!");
            dispose();
            
        }
        catch(ValidacaoException ex){
        	JOptionPane.showMessageDialog(this, ex.getMessage());
        }

       
        
    }
}