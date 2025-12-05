package Projeto;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TelaPrincipalCoordenador extends TelaPrincipalBase {
	
    private JTextField txtPesquisa;

    public TelaPrincipalCoordenador(CentralDeInformacoes central, Persistencia p) {
        super("Painel do Coordenador", central, p);
    }
    
    // --- AÇÕES ---

    public void cadastroEdital() {
        // Abre a tela de cadastro para um NOVO edital
    	new TelaCadastroEdital(central, p);
    }
    
    private void editarEditalSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um edital na tabela para editar.");
            return;
        }
        
        long idEdital = (Long) model.getValueAt(linha, 0); 
        EditalDeMonitoria edital = buscarEditalPorId(idEdital);

        if (edital != null) {
            new TelaCadastroEdital(central, p, edital);
        }
    }

    private void verEditalSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um edital para visualizar.");
            return;
        }

        long idEdital = (Long) model.getValueAt(linha, 0);
        EditalDeMonitoria editalSelecionado = buscarEditalPorId(idEdital);
        
        if (editalSelecionado != null) {
            new TelaDetalhesEdital(this, editalSelecionado);
        }
    }

    private void excluirEditalSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um edital para excluir.");
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este edital?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            long idEdital = (Long) model.getValueAt(linha, 0);
            
            boolean removido = false;
            List<EditalDeMonitoria> lista = central.getTodosEditais();
            
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == idEdital) {
                    lista.remove(i);
                    removido = true;
                    break;
                }
            }

            if (removido) {
                p.salvarCentral(central); 
                model.removeRow(linha);   
                JOptionPane.showMessageDialog(this, "Edital excluído.");
            }
        }
    }
    
    private EditalDeMonitoria buscarEditalPorId(long id) {
        for(EditalDeMonitoria e : central.getTodosEditais()) {
            if(e.getId() == id) {
                return e;
            }
        }
        return null;
    }
    
    private void filtrarEditais() {
        String termo = txtPesquisa.getText().toLowerCase();
        List<EditalDeMonitoria> todos = central.getTodosEditais();
        
        List<EditalDeMonitoria> filtrados = todos.stream()
            .filter(e -> e.getTitulo().toLowerCase().contains(termo) || String.valueOf(e.getId()).contains(termo))
            .collect(Collectors.toList());
            
        carregarDadosEditais(filtrados); 
    }
    
    @Override
    protected void montarConteudoEspecifico() {
    	
        // --- CABEÇALHO GLOBAL ---
        // Botão Perfil (Topo Direita)
    	JButton btnPerfil = criarBotao("Perfil", 870, 30, 80, 25, e -> new Perfil(central, true, "", p));
        estilizar(btnPerfil, 12, false);
        btnPerfil.setBackground(new Color(230, 230, 230));
    	
    	// --- MENU LATERAL (Esquerda) ---
        // Botão para recarregar a lista completa
        criarBotao("Atualizar Lista", 30, 120, 150, 40, e -> {
            txtPesquisa.setText("");
            carregarDadosTabela();
        });
    	
    	// --- ÁREA DE CONTEÚDO (Centro/Direita) ---
        
        // BARRA DE PESQUISA (Posicionada no topo da área de conteúdo, à direita do título)
        int ySearch = 105; 
        criarLabel("Pesquisar:", 520, ySearch, 70, 25);
        
        txtPesquisa = criarCampoTexto(590, ySearch, 180, 25);
        txtPesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarEditais(); 
            }
        });
        
        JButton btnBuscar = criarBotao("Buscar", 780, ySearch, 80, 25, e -> filtrarEditais());
        btnBuscar.setBackground(new Color(100, 149, 237));
        btnBuscar.setForeground(Color.BLACK);
        
        // Botão Limpar Busca (Pequeno X)
        JButton btnLimpar = criarBotao("X", 870, ySearch, 45, 25, e -> {
            txtPesquisa.setText("");
            carregarDadosTabela();
        });
        btnLimpar.setBackground(new Color(220, 220, 220));

        // DUPLO CLIQUE NA TABELA
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verEditalSelecionado(); 
                }
            }
        });
    	
        // --- RODAPÉ (Botões de Ação) ---
        // Posicionados abaixo da tabela (Y=510)
        int yBotoes = 510;
        int xStart = 250; // Começa alinhado com a tabela
        int gap = 140;    // Espaçamento entre botões

        // 1. VER
        JButton btnVer = criarBotao("Ver Detalhes", xStart, yBotoes, 120, 35, e -> verEditalSelecionado());
        btnVer.setBackground(new Color(255, 140, 0)); // Laranja
        btnVer.setForeground(Color.BLACK);

        // 2. EDITAR
        JButton btnEditar = criarBotao("Editar", xStart + gap, yBotoes, 100, 35, e -> editarEditalSelecionado());
        btnEditar.setBackground(new Color(0, 102, 204)); // Azul
        btnEditar.setForeground(Color.BLACK);

        // 3. EXCLUIR
        JButton btnExcluir = criarBotao("Excluir", xStart + gap*2, yBotoes, 100, 35, e -> excluirEditalSelecionado());
        btnExcluir.setBackground(new Color(204, 0, 0)); // Vermelho
        btnExcluir.setForeground(Color.BLACK);

        // 4. NOVO EDITAL (Alinhado mais à direita para destaque)
        JButton btnNovoEdital = criarBotao("+ Novo Edital", 780, yBotoes, 140, 35, e -> cadastroEdital());
        btnNovoEdital.setBackground(new Color(0, 153, 76)); // Verde Forte
        btnNovoEdital.setForeground(Color.BLACK);
        btnNovoEdital.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }
}