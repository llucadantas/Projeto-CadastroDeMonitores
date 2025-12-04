package Projeto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TelaCadastroEdital extends BaseTelas {

    private JTextField campoDataInicio;
    private JTextField campoDataFim;
    private JTextField campoMaxInscricoes;
    private JTextField campoPesoCRE;
    private JTextField campoPesoMedia;

    private JTextField campoNomeDisciplina;
    private JTextField campoVagasRemuneradas;
    private JTextField campoVagasVoluntarios;

    private DefaultTableModel tabelaModel;
    private JTable tabela;
    private CentralDeInformacoes central;
    private Persistencia p;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public TelaCadastroEdital(CentralDeInformacoes c, Persistencia p) {
        super("Cadastro de Edital de Monitoria", 750, 650);
        this.central = c;
        this.p = p;
    }

    @Override
    protected void montarTela() {

        painel.setLayout(null);

        // --------------------------- TÍTULO ---------------------------
        JLabel titulo = criarLabel("Cadastro de Edital de Monitoria", 0, 10, 750, 40);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        estilizar(titulo, 22, true);

        // --------------------------- PAINEL DADOS GERAIS ---------------------------
        JPanel painelGeral = new JPanel(null);
        painelGeral.setBounds(40, 70, 670, 140);
        painelGeral.setBorder(BorderFactory.createTitledBorder("Informações do Edital"));
        painel.add(painelGeral);

        criarLabel("Data Início (dd/MM/yyyy):", 20, 30, 200, 25, painelGeral);
        campoDataInicio = criarCampoTexto(200, 30, 150, 25, painelGeral);

        criarLabel("Data Fim (dd/MM/yyyy):", 370, 30, 200, 25, painelGeral);
        campoDataFim = criarCampoTexto(520, 30, 120, 25, painelGeral);

        criarLabel("Máx. inscrições por aluno:", 20, 70, 200, 25, painelGeral);
        campoMaxInscricoes = criarCampoTexto(200, 70, 80, 25, painelGeral);

        // --------------------------- PAINEL PESOS ---------------------------
        JPanel painelPesos = new JPanel(null);
        painelPesos.setBounds(40, 220, 670, 100);
        painelPesos.setBorder(BorderFactory.createTitledBorder("Pesos da Fórmula"));
        painel.add(painelPesos);

        criarLabel("Peso CRE:", 20, 30, 150, 25, painelPesos);
        campoPesoCRE = criarCampoTexto(120, 30, 80, 25, painelPesos);

        criarLabel("Peso Média:", 240, 30, 150, 25, painelPesos);
        campoPesoMedia = criarCampoTexto(350, 30, 80, 25, painelPesos);

        JLabel aviso = criarLabel("(A soma dos pesos deve ser 1.0)", 450, 30, 200, 25, painelPesos);
        aviso.setForeground(Color.DARK_GRAY);

        // --------------------------- PAINEL DISCIPLINAS ---------------------------
        JPanel painelDisc = new JPanel(null);
        painelDisc.setBounds(40, 330, 670, 110);
        painelDisc.setBorder(BorderFactory.createTitledBorder("Adicionar Disciplina"));
        painel.add(painelDisc);

        criarLabel("Nome:", 20, 30, 100, 25, painelDisc);
        campoNomeDisciplina = criarCampoTexto(80, 30, 200, 25, painelDisc);

        criarLabel("Vagas Rem.:", 300, 30, 100, 25, painelDisc);
        campoVagasRemuneradas = criarCampoTexto(390, 30, 70, 25, painelDisc);

        criarLabel("Vagas Vol.:", 480, 30, 100, 25, painelDisc);
        campoVagasVoluntarios = criarCampoTexto(560, 30, 70, 25, painelDisc);

        criarBotao("Adicionar", 520, 65, 120, 30, e -> adicionarDisciplina(), painelDisc);

        // --------------------------- TABELA ---------------------------
        tabelaModel = new DefaultTableModel(new String[]{"Disciplina", "Remuneradas", "Voluntários"}, 0);
        tabela = new JTable(tabelaModel);
        tabela.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(40, 450, 670, 130);
        painel.add(scroll);

        // --------------------------- BOTÃO SALVAR ---------------------------
        criarBotao("Salvar Edital", 300, 590, 180, 40, e -> salvarEdital());
        
        criarBotao("Fechar", 500, 590, 120, 40, e -> dispose());

        setTelaCheia();
    }

    // ======================================================================
    // -------------------------- FUNÇÕES ----------------------------------
    // ======================================================================

    private void adicionarDisciplina() {
        String nome = campoNomeDisciplina.getText();
        String r = campoVagasRemuneradas.getText();
        String v = campoVagasVoluntarios.getText();

        if (nome.isEmpty() || r.isEmpty() || v.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos da disciplina!");
            return;
        }
        
        try {
            Integer.parseInt(r);
            Integer.parseInt(v);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vagas devem ser numéricas!");
            return;
        }

        tabelaModel.addRow(new Object[]{nome, r, v});

        campoNomeDisciplina.setText("");
        campoVagasRemuneradas.setText("");
        campoVagasVoluntarios.setText("");
    }

    private void salvarEdital() {
        try {
            Date inicio = sdf.parse(campoDataInicio.getText());
            Date fim = sdf.parse(campoDataFim.getText());

            if (fim.before(inicio)) {
                JOptionPane.showMessageDialog(this, "A data fim deve ser depois da data início!");
                return;
            }

            int max = Integer.parseInt(campoMaxInscricoes.getText());
            double pesoCRE = Double.parseDouble(campoPesoCRE.getText());
            double pesoMedia = Double.parseDouble(campoPesoMedia.getText());

            if (pesoCRE + pesoMedia != 1.0) {
                JOptionPane.showMessageDialog(this, "A soma dos pesos deve ser exatamente 1.0!");
                return;
            }

            if (tabelaModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Adicione pelo menos uma disciplina!");
                return;
            }
            
            ArrayList<Disciplina> disciplinas = new ArrayList<>();
            
         // Percorre todas as linhas
            for (int i = 0; i < tabelaModel.getRowCount(); i++) {
                
                // Pega o valor da coluna 0 (ex: Nome) da linha atual 'i'
                // O retorno é Object, então você deve fazer o cast (converter)
                String nome = (String) tabelaModel.getValueAt(i, 0);
                
                // Pega o valor da coluna 1 (ex: Email)
                int r = Integer.parseInt(tabelaModel.getValueAt(i, 1).toString());
                int v = Integer.parseInt(tabelaModel.getValueAt(i, 2).toString());
                
                Disciplina d = new Disciplina(nome, r, v);
                
                disciplinas.add(d);
            }

            EditalDeMonitoria edital = new EditalDeMonitoria("",inicio, fim, max, pesoMedia, pesoMedia, disciplinas);
            
            central.adicionarEdital(edital);
            p.salvarCentral(central);
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            dispose();
            new TelaPrincipalCoordenador(central, p);
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Datas inválidas! Use o formato dd/MM/yyyy.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Campos numéricos inválidos!");
        }
    }

}
