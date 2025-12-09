package Projeto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TelaDetalhesEdital extends JDialog {

    private final Color COR_FUNDO = Color.WHITE;
    private final Color COR_PRIMARIA = new Color(0, 102, 204); // Azul
    // 🎨 Nova cor para o botão de ação (Gerar Resultado)
    private final Color COR_ACAO = new Color(76, 175, 80); // Verde
    
    private final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    private final Font FONTE_TEXTO = new Font("Segoe UI", Font.PLAIN, 12);
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public TelaDetalhesEdital(Frame parent, EditalDeMonitoria edital) {
        super(parent, "Detalhes do Edital", false); // 'true' torna a janela Modal (bloqueia a de trás)
        
        setSize(600, 500);
        setLocationRelativeTo(parent); // Centraliza em relação à janela principal
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_FUNDO);

        montarCabecalho(edital);
        montarCorpo(edital);
        montarRodape(edital); // Método modificado
        
        setVisible(true);
    }

    private void montarCabecalho(EditalDeMonitoria edital) {
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(COR_PRIMARIA);
        painelTopo.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel(edital.getTitulo().toUpperCase());
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel("Edital Nº " + edital.getId());
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(200, 220, 255));

        painelTopo.add(lblTitulo, BorderLayout.NORTH);
        painelTopo.add(lblSubtitulo, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);
    }

    private void montarCorpo(EditalDeMonitoria edital) {
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(COR_FUNDO);
        painelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- SEÇÃO 1: INFORMAÇÕES GERAIS ---
        JPanel painelInfos = new JPanel(new GridLayout(2, 2, 10, 10));
        painelInfos.setBackground(COR_FUNDO);
        painelInfos.setBorder(BorderFactory.createTitledBorder("Informações Gerais"));
        painelInfos.setMaximumSize(new Dimension(600, 100)); // Limita altura

        adicionarInfo(painelInfos, "Início das Inscrições:", sdf.format(edital.getInicioInscricoes()));
        adicionarInfo(painelInfos, "Fim das Inscrições:", sdf.format(edital.getFimInscricoes()));
        adicionarInfo(painelInfos, "Peso CRE:", String.valueOf(edital.getPesoCRE()));
        adicionarInfo(painelInfos, "Peso Média:", String.valueOf(edital.getPesoMedia()));

        painelCentral.add(painelInfos);
        painelCentral.add(Box.createVerticalStrut(20)); // Espaço

        // --- SEÇÃO 2: TABELA DE DISCIPLINAS ---
        JLabel lblDisc = new JLabel("Disciplinas Ofertadas");
        lblDisc.setFont(FONTE_LABEL);
        lblDisc.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCentral.add(lblDisc);
        painelCentral.add(Box.createVerticalStrut(5));

        // Configuração da Tabela
        String[] colunas = {"Disciplina", "Vagas Remuneradas", "Vagas Voluntárias"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        for (Disciplina d : edital.getDisciplinas()) {
            model.addRow(new Object[]{d.getNome(), d.getnVagasRem(), d.getnVagasVol()});
        }

        JTable tabela = new JTable(model);
        tabela.setRowHeight(25);
        tabela.getTableHeader().setBackground(new Color(240, 240, 240));
        
        JScrollPane scroll = new JScrollPane(tabela);
        painelCentral.add(scroll);

        add(painelCentral, BorderLayout.CENTER);
    }

    private void adicionarInfo(JPanel painel, String titulo, String valor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(COR_FUNDO);
        
        JLabel lblT = new JLabel(titulo + " ");
        lblT.setFont(FONTE_LABEL);
        
        JLabel lblV = new JLabel(valor);
        lblV.setFont(FONTE_TEXTO);
        lblV.setForeground(Color.DARK_GRAY);
        
        p.add(lblT);
        p.add(lblV);
        painel.add(p);
    }

    private void montarRodape(EditalDeMonitoria edital) {
        JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Adicionado 10px de hgap
        painelSul.setBackground(new Color(245, 245, 245));
        painelSul.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        
        JButton btnGerarResultado = new JButton("Gerar Resultado");
        btnGerarResultado.setFont(FONTE_LABEL);
        btnGerarResultado.setBackground(COR_ACAO); // Usa a nova cor verde
        btnGerarResultado.setForeground(Color.WHITE);
        btnGerarResultado.setFocusPainted(false);
        btnGerarResultado.setPreferredSize(new Dimension(150, 35));
        
        btnGerarResultado.addActionListener(e -> {
            new TelaCalcularResultadoEdital(edital.getId());
        });
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(FONTE_LABEL);
        btnFechar.setBackground(new Color(100, 100, 100));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setPreferredSize(new Dimension(100, 35));
        
        btnFechar.addActionListener(e -> this.dispose()); // Fecha a janela
        
        painelSul.add(btnGerarResultado);
        painelSul.add(btnFechar);
        
        add(painelSul, BorderLayout.SOUTH);
    }
}