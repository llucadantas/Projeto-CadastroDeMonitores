package Projeto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipalCoordenador extends BaseTelas {
	
	private DefaultTableModel model; // ATRIBUTO: Pertence à classe
    private CentralDeInformacoes central; // ATRIBUTO: Necessário para acessar os dados

    // CONSTRUTOR PRINCIPAL: Injeta a Central
    public TelaPrincipalCoordenador(CentralDeInformacoes central) {
        super("Painel do Coordenador", 1000, 700);
        this.central = central; // ✅ Inicializa o estado (Central)
        setTelaCheia(); 
        montarTela(); // ✅ Chama montarTela SÓ DEPOIS da inicialização
    }
    
    // CONSTRUTOR DE TESTE: Para permitir a execução direta do main
    public TelaPrincipalCoordenador() {
        // Inicializa com uma Central de Informações de teste
        this(new CentralDeInformacoes()); 
    }
    
    
    /**
     * Carrega dados de uma lista de Editais para o DefaultTableModel.
     * @param listaEditais Lista de objetos EditalDeMonitoria.
     */
    private void carregarDadosEditais(List<EditalDeMonitoria> listaEditais) {
        
        // Verificação de segurança (embora montarTela garanta que não é nulo)
        if (model == null) { return; } 
        
        model.setRowCount(0); // 1. Limpa a tabela
        
        // 2. Itera sobre a lista de editais
        for (EditalDeMonitoria edital : listaEditais) {
            try {
                // 3. Adiciona a linha (Requer EditalDeMonitoria.toObjectArray())
                model.addRow(edital.toObjectArray()); 
            } catch (Exception e) {
                // Captura exceções que podem ocorrer ao acessar dados nulos do Edital
                System.err.println("Erro ao adicionar edital à tabela: " + e.getMessage());
            }
        }
    }

    @Override
    protected void montarTela() {
        
        // ... (Configuração de botões e layout omitida por brevidade, mas está correta) ...

        JButton btnPerfil = criarBotao("Perfil", 870, 30, 60, 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Perfil(); 
            }
        });
 
        criarBotaoLink("Sair / Logout", 850, 60, 100, 20, e -> {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) { dispose(); }
        });

        // ... (Layout de Menu e Separador) ...
        JLabel lblMenu = criarLabel("Menu Principal", 30, 80, 200, 30);
        estilizar(lblMenu, 16, true);
        lblMenu.setForeground(new Color(0, 102, 204));
        criarBotao("Gerenciar Editais", 30, 120, 180, 40, e -> System.out.println("Abrir Editais"));
        // ... (Separador) ...
        JLabel lblTituloLista = criarLabel("Editais Recentes", 250, 100, 300, 30);
        estilizar(lblTituloLista, 18, true);

   
        String[] colunas = {"ID", "Título do Edital", "Data Início", "Data Fim", "Status"};
        
        // 1. Inicializa o ATRIBUTO 'model' com 0 linhas (Correto)
        this.model = new DefaultTableModel(colunas, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                return false;
             }
        };

        JTable tabela = new JTable(this.model);
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(250, 140, 600, 350);
        painel.add(scrollPane);
        
        // 2. CHAMA O CARREGAMENTO DE DADOS (Agora seguro, pois 'central' não é nulo)
        List<EditalDeMonitoria> listaEditais = central.getTodosEditais();
        carregarDadosEditais(listaEditais); 
        
        // ... (Botão Novo Edital) ...
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
        
        // 🚨 CHAMA O CONSTRUTOR DE TESTE (Que chama o construtor principal)
        new TelaPrincipalCoordenador(); 
    }
}