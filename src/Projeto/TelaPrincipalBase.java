package Projeto;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class TelaPrincipalBase extends BaseTelas {
	
	protected CentralDeInformacoes central;
	protected DefaultTableModel model;
    protected JTable tabela; // AJUSTE: Necessário ser atributo para as classes filhas acessarem (ex: getSelectedRow)
	protected Persistencia p;
	
	protected static final int LARGURA_MENU = 210;
	protected static final int X_CONTEUDO = 250; 
	protected static final int Y_TOPO = 100;
	protected static final int TABELA_Y_START = 140;

    public TelaPrincipalBase(String titulo, CentralDeInformacoes central, Persistencia p) {
        super(titulo, 1000, 700);
        this.central = central;
        this.p = p;
        
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 250));
        
        
        montarBase(); 
        montarTabelaEditais(); 
        montarConteudoEspecifico();
        
        revalidate();
        repaint();
        setVisible(true);
    }
    
    protected abstract void montarConteudoEspecifico();
    
    private final void montarBase() {
        criarBotaoLink("Sair / Logout", 850, 60, 100, 20, e -> {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) { 
                dispose(); 
                new TelaLogin(central, p);
            }
        });

        JLabel lblMenu = criarLabel("Menu Principal", 30, 80, 200, 30);
        estilizar(lblMenu, 16, true);
        lblMenu.setForeground(new Color(0, 102, 204));
        
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBounds(LARGURA_MENU, 20, 2, 600);
        separator.setForeground(Color.LIGHT_GRAY);
        painel.add(separator);
    }

    protected void montarTabelaEditais() {
        
        JLabel lblTituloLista = criarLabel("Editais Recentes", X_CONTEUDO, Y_TOPO, 300, 30);
        estilizar(lblTituloLista, 18, true);

        String[] colunas = {"ID", "Título do Edital", "Data Início", "Data Fim", "Status"};
        
        this.model = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                return false;
             }
        };

        this.tabela = new JTable(this.model);
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(X_CONTEUDO, TABELA_Y_START, 680, 350); // Ajustei largura para 680
        painel.add(scrollPane);
        
        carregarDadosTabela();
    }
    

    protected void carregarDadosTabela() {
        if(central != null) {
            carregarDadosEditais(central.getTodosEditais());
        }
    }
    
    
    
    protected void carregarDadosEditais(List<EditalDeMonitoria> listaEditais) {
        if (model == null) { return; } 
        
        model.setRowCount(0); 
        
        for (EditalDeMonitoria edital : listaEditais) {
            try {
                // Monta a linha manualmente para garantir que funcione mesmo sem toObjectArray()
                Object[] linha = edital.toObjectArray();
                model.addRow(linha); 
            } catch (Exception e) {
                System.err.println("Erro ao adicionar edital à tabela: " + e.getMessage());
            }
        }
    }

    @Override
    protected void montarTela() {
    }
}