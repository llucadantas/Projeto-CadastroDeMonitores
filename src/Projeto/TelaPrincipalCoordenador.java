package Projeto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipalCoordenador extends BaseTelas {


    public TelaPrincipalCoordenador() {
        super("Painel do Coordenador", 1000, 700);
        setTelaCheia(); 
    }

    @Override
    protected void montarTela() {
        
      
        JButton btnPerfil = criarBotao("Perfil", 870, 30, 60, 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                new Perfil(); 
            }
        });
 
      
        criarBotaoLink("Sair / Logout", 850, 60, 100, 20, e -> {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                dispose(); 
                
            }
        });

        JLabel lblMenu = criarLabel("Menu Principal", 30, 80, 200, 30);
        estilizar(lblMenu, 16, true);
        lblMenu.setForeground(new Color(0, 102, 204));

        int btnY = 120;
        int btnW = 180;
        int btnH = 40;
        int gap = 10;

        criarBotao("Gerenciar Editais", 30, btnY, btnW, btnH, e -> System.out.println("Abrir Editais"));

       
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBounds(230, 20, 2, 540);
        separator.setForeground(Color.LIGHT_GRAY);
        painel.add(separator);

        JLabel lblTituloLista = criarLabel("Editais Recentes", 250, 100, 300, 30);
        estilizar(lblTituloLista, 18, true);

   
        String[] colunas = {"ID", "Título do Edital", "Data Início", "Data Fim", "Status"};
        
        Object[][] dados = {
            {"001", "Iniciação Científica 2024", "01/02/2024", "01/03/2024", "Aberto"},
            {"002", "Extensão Tecnológica", "15/02/2024", "15/04/2024", "Em Análise"},
            {"003", "Bolsa Pesquisa Avançada", "01/03/2024", "30/03/2024", "Fechado"},
            {"004", "Monitoria Java 2024.1", "10/01/2024", "20/01/2024", "Concluído"},
        };

        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            
        };

        JTable tabela = new JTable(model);
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(250, 140, 600, 350);
        painel.add(scrollPane);

        
        JButton btnNovoEdital = criarBotao("Novo Edital", 730, 510, 120, 35, e -> {
            JOptionPane.showMessageDialog(this, "Abrir formulário de cadastro...");
        });
        btnNovoEdital.setBackground(new Color(0, 153, 76)); 
        btnNovoEdital.setForeground(Color.WHITE); 
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TelaPrincipalCoordenador();
    }
}