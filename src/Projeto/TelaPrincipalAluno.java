package Projeto;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class TelaPrincipalAluno extends TelaPrincipalBase {
	
	private String matricula;
	
	
    public TelaPrincipalAluno(CentralDeInformacoes central, String m, Persistencia p) {
        super("Painel do Aluno: " + central.recuperarAlunoPorMatricula(m).getNome(), central, p);
        this.matricula = m;
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
    
    private EditalDeMonitoria buscarEditalPorId(long id) {
        for(EditalDeMonitoria e : central.getTodosEditais()) {
            if(e.getId() == id) {
                return e;
            }
        }
        return null;
    }
    
    
    @Override
    protected void montarConteudoEspecifico() {
    	
        JButton btnPerfil = criarBotao("Perfil", 870, 30, 60, 20, e -> new Perfil(central, false, matricula, p));
        estilizar(btnPerfil, 10, false);
        btnPerfil.setBackground(new Color(230, 230, 230));
    	
    	// 1. Botões de Menu específicos
        criarBotao("Gerenciar Editais", 30, 120, 180, 40, e -> System.out.println("Abrir Gerenciamento de Editais"));
    	
        // 2. Ação específica: Botão Novo Edital
        JButton btnNovoEdital = criarBotao("Inscrever-se", 730, 510, 120, 35, e -> setForeground(Color.BLACK));

        btnNovoEdital.setBackground(new Color(0, 153, 76)); 
        btnNovoEdital.setForeground(Color.BLACK);
        
        JButton btnVer = criarBotao("Ver Detalhes", 250, 510, 120, 35, e -> verEditalSelecionado());
        btnVer.setBackground(new Color(255, 140, 0)); // Laranja
        btnVer.setForeground(Color.BLACK);
        
        // A tabela de editais (JTable) e o modelo (model) já foram criados e populados
        // pelo método montarTabelaEditais() na classe pai.
    }
}