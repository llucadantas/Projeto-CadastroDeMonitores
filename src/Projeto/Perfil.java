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

    public Perfil() {
        super("Meu Perfil", 500, 520);
        // DISPOSE_ON_CLOSE é vital aqui: fecha só esta janela, não o programa todo
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    protected void montarTela() {
        // --- SEÇÃO 1: DADOS PESSOAIS ---
        JLabel lblDados = criarLabel("Dados Pessoais", 30, 20, 200, 30);
        estilizar(lblDados, 16, true);
        lblDados.setForeground(new Color(0, 102, 204));

        criarLabel("Nome Completo:", 30, 60, 150, 20);
        txtNome = criarCampoTexto(30, 80, 420, 30);
        txtNome.setText("Coordenador Carlos"); 

        criarLabel("E-mail Institucional:", 30, 120, 150, 20);
        txtEmail = criarCampoTexto(30, 140, 420, 30);
        txtEmail.setText("carlos.coord@faculdade.edu.br");

        criarLabel("Login (Imutável):", 30, 180, 150, 20);
        txtLogin = criarCampoTexto(30, 200, 200, 30);
        txtLogin.setText("admin.carlos");
        txtLogin.setEditable(false);
        txtLogin.setBackground(new Color(230, 230, 230));
        txtLogin.setForeground(Color.GRAY);

        
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

      
        if (nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha Nome e E-mail.", 
                "Campo Obrigatório", 
                JOptionPane.WARNING_MESSAGE);
            return; // Para a execução aqui
        }

       
        if (!s1.isEmpty() || !s2.isEmpty()) {
            if (!s1.equals(s2)) {
                JOptionPane.showMessageDialog(this, 
                    "As senhas digitadas não conferem!", 
                    "Erro de Senha", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (s1.length() < 4) {
                JOptionPane.showMessageDialog(this, 
                    "A senha deve ter no mínimo 4 caracteres.", 
                    "Senha Curta", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        
        System.out.println("UPDATE Usuario SET nome='" + nome + "' WHERE login='admin.carlos'");
        
        JOptionPane.showMessageDialog(this, 
            "Dados do perfil atualizados com sucesso!", 
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);

        dispose(); 
    }
}