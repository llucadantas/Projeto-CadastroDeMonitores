package Projeto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TelaCadastroEdital extends BaseTelas {

    private JTextField campoTitulo;
    private JFormattedTextField campoDataInicio;
    private JFormattedTextField campoDataFim;
    private JTextField campoMaxInscricoes;
    private JTextField campoPesoCRE;
    private JTextField campoPesoMedia;
    private JTextField campoNomeDisciplina;
    private JTextField campoVagasRemuneradas;
    private JTextField campoVagasVoluntarios;

    private DefaultTableModel tabelaModel;
    private CentralDeInformacoes central;
    private Persistencia p;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private EditalDeMonitoria editalEdicao = null;

    public TelaCadastroEdital(CentralDeInformacoes c, Persistencia p) {
        super("Cadastro de Edital", 750, 700);
        this.central = c;
        this.p = p;
    }

    public TelaCadastroEdital(CentralDeInformacoes c, Persistencia p, EditalDeMonitoria edital) {
        super("Editar Edital", 750, 700);
        this.central = c;
        this.p = p;
        this.editalEdicao = edital;
        
        preencherDadosEdicao();
    }

    private void preencherDadosEdicao() {
        if (editalEdicao == null) return;
        
        SwingUtilities.invokeLater(() -> {
            if (campoTitulo != null) campoTitulo.setText(editalEdicao.getTitulo());
            if (campoMaxInscricoes != null) campoMaxInscricoes.setText(String.valueOf(editalEdicao.getMaximoInscricoes()));
            if (campoPesoCRE != null) campoPesoCRE.setText(String.valueOf(editalEdicao.getPesoCRE()));
            if (campoPesoMedia != null) campoPesoMedia.setText(String.valueOf(editalEdicao.getPesoMedia()));
            
            if (editalEdicao.getInicioInscricoes() != null) 
                campoDataInicio.setText(sdf.format(editalEdicao.getInicioInscricoes()));
            if (editalEdicao.getFimInscricoes() != null) 
                campoDataFim.setText(sdf.format(editalEdicao.getFimInscricoes()));
    
            if (tabelaModel != null) {
                tabelaModel.setRowCount(0); // Limpa antes de adicionar
                for (Disciplina d : editalEdicao.getDisciplinas()) {
                    tabelaModel.addRow(new Object[]{ d.getNome(), d.getnVagasRem(), d.getnVagasVol() });
                }
            }
        });
    }

    @Override
    protected void montarTela() {
        painel.setLayout(null);
        String txtTitulo = (editalEdicao == null) ? "Cadastro de Edital" : "Editando Edital";
        JLabel titulo = criarLabel(txtTitulo, 0, 10, 750, 40);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        estilizar(titulo, 22, true);

        JPanel painelGeral = new JPanel(null);
        painelGeral.setBounds(40, 70, 670, 180);
        painelGeral.setBorder(BorderFactory.createTitledBorder("Informações"));
        painel.add(painelGeral);

        criarLabel("Título:", 20, 30, 100, 25, painelGeral);
        campoTitulo = criarCampoTexto(80, 30, 560, 25, painelGeral);

        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            criarLabel("Início:", 20, 70, 50, 25, painelGeral);
            campoDataInicio = new JFormattedTextField(mask);
            campoDataInicio.setBounds(80, 70, 120, 25);
            painelGeral.add(campoDataInicio);
            
            criarLabel("Fim:", 220, 70, 50, 25, painelGeral);
            campoDataFim = new JFormattedTextField(mask);
            campoDataFim.setBounds(260, 70, 120, 25);
            painelGeral.add(campoDataFim);
        } catch (Exception e) {}

        criarLabel("Máx. Insc.:", 400, 70, 80, 25, painelGeral);
        campoMaxInscricoes = criarCampoTexto(480, 70, 60, 25, painelGeral);

        JPanel painelPesos = new JPanel(null);
        painelPesos.setBounds(40, 260, 670, 80);
        painelPesos.setBorder(BorderFactory.createTitledBorder("Pesos (Soma 1.0)"));
        painel.add(painelPesos);
        
        criarLabel("Peso CRE:", 20, 30, 80, 25, painelPesos);
        campoPesoCRE = criarCampoTexto(100, 30, 80, 25, painelPesos);
        criarLabel("Peso Média:", 200, 30, 80, 25, painelPesos);
        campoPesoMedia = criarCampoTexto(290, 30, 80, 25, painelPesos);

        JPanel painelDisc = new JPanel(null);
        painelDisc.setBounds(40, 350, 670, 80);
        painelDisc.setBorder(BorderFactory.createTitledBorder("Nova Disciplina"));
        painel.add(painelDisc);
        
        criarLabel("Nome:", 10, 30, 50, 25, painelDisc);
        campoNomeDisciplina = criarCampoTexto(60, 30, 200, 25, painelDisc);
        criarLabel("Rem:", 270, 30, 40, 25, painelDisc);
        campoVagasRemuneradas = criarCampoTexto(310, 30, 40, 25, painelDisc);
        criarLabel("Vol:", 360, 30, 40, 25, painelDisc);
        campoVagasVoluntarios = criarCampoTexto(400, 30, 40, 25, painelDisc);
        
        criarBotao("Add", 460, 28, 80, 30, e -> adicionarDisciplina(), painelDisc);

        tabelaModel = new DefaultTableModel(new String[]{"Disciplina", "Rem", "Vol"}, 0);
        JScrollPane scroll = new JScrollPane(new JTable(tabelaModel));
        scroll.setBounds(40, 440, 670, 180);
        painel.add(scroll);

        String btnTxt = (editalEdicao == null) ? "Salvar" : "Atualizar";
        criarBotao(btnTxt, 250, 630, 150, 40, e -> salvar());
        criarBotao("Cancelar", 420, 630, 120, 40, e -> dispose());
        
        setTelaCheia();
        
        if(editalEdicao != null) preencherDadosEdicao();
    }
    
    private void adicionarDisciplina() {
        try {
            String n = campoNomeDisciplina.getText();
            int r = Integer.parseInt(campoVagasRemuneradas.getText());
            int v = Integer.parseInt(campoVagasVoluntarios.getText());
            if (!n.isEmpty()) {
                tabelaModel.addRow(new Object[]{n, r, v});
                campoNomeDisciplina.setText(""); campoVagasRemuneradas.setText(""); campoVagasVoluntarios.setText("");
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro nas vagas"); }
    }

    private void salvar() {
        try {
            String titulo = campoTitulo.getText();
            Date i = sdf.parse(campoDataInicio.getText());
            Date f = sdf.parse(campoDataFim.getText());
            int max = Integer.parseInt(campoMaxInscricoes.getText());
            double p1 = Double.parseDouble(campoPesoCRE.getText());
            double p2 = Double.parseDouble(campoPesoMedia.getText());
            
            ArrayList<Disciplina> discs = new ArrayList<>();
            for(int k=0; k<tabelaModel.getRowCount(); k++) {
                discs.add(new Disciplina(
                    (String)tabelaModel.getValueAt(k,0),
                    Integer.parseInt(tabelaModel.getValueAt(k,1).toString()),
                    Integer.parseInt(tabelaModel.getValueAt(k,2).toString())
                ));
            }

            if (editalEdicao == null) {
                central.adicionarEdital(new EditalDeMonitoria(titulo, i, f, max, p1, p2, discs));
            } else {
                editalEdicao.setTitulo(titulo); editalEdicao.setInicioInscricoes(i); editalEdicao.setFimInscricoes(f);
                editalEdicao.setMaximoInscricoes(max);; editalEdicao.setPesoCRE(p1); editalEdicao.setPesoMedia(p2);
                editalEdicao.setDisciplinas(discs);
            }
            p.salvarCentral(central);
            JOptionPane.showMessageDialog(this, "Salvo com Sucesso!");
            dispose();
            new TelaPrincipalCoordenador(central, p);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage()); }
    }
}