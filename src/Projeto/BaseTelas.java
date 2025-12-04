package Projeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class BaseTelas extends JFrame {

    protected JPanel painel; // Painel principal onde os componentes são colocados
    private JPanel fundo;    // Fundo que centraliza o painel

    public BaseTelas(String titulo, int largura, int altura) {
        super(titulo);

        fundo = new JPanel(new GridBagLayout());
        fundo.setBackground(new Color(240, 240, 240));
        setContentPane(fundo);

        painel = new JPanel();
        painel.setLayout(null);
        painel.setBackground(Color.WHITE);
        painel.setPreferredSize(new Dimension(largura, altura));
        painel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        fundo.add(painel);

        setSize(largura + 50, altura + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        montarTela();
        setVisible(true);
    }

    protected void setTelaCheia() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    protected abstract void montarTela();

    // ============================================================
    // ============ MÉTODOS ORIGINAIS (COMPATÍVEIS) ===============
    // ============================================================

    protected JLabel criarLabel(String texto, int x, int y, int w, int h) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, w, h);
        painel.add(label);
        return label;
    }

    protected JTextField criarCampoTexto(int x, int y, int w, int h) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, w, h);
        painel.add(campo);
        return campo;
    }
    
    protected JPasswordField criarCampoSenha(int x, int y, int w, int h) {
    	JPasswordField campo = new JPasswordField();
    	campo.setBounds(x, y, w, h);
    	painel.add(campo);
    	return campo;
    }

    protected JButton criarBotao(String texto, int x, int y, int w, int h, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, w, h);
        botao.addActionListener(acao);
        painel.add(botao);
        return botao;
    }
    
    protected JButton criarBotaoLink(String texto, int x, int y, int w, int h, ActionListener acao) {
    	JButton botao = new JButton(texto);
    	botao.setBounds(x, y, w, h);
    	botao.setBorderPainted(false);
    	botao.setContentAreaFilled(false);
    	botao.setFocusPainted(false);
    	botao.setOpaque(false);
    	botao.setForeground(Color.BLUE);
    	botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	botao.addActionListener(acao);
    	painel.add(botao);
    	return botao;
    }

    // ============================================================
    // === NOVAS SOBRECARGAS PARA ADICIONAR EM OUTRO PAINEL =======
    // ============================================================

    protected JLabel criarLabel(String texto, int x, int y, int w, int h, JPanel destino) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, w, h);
        destino.add(label);
        return label;
    }

    protected JTextField criarCampoTexto(int x, int y, int w, int h, JPanel destino) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, w, h);
        destino.add(campo);
        return campo;
    }

    protected JButton criarBotao(String texto, int x, int y, int w, int h, ActionListener acao, JPanel destino) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, w, h);
        botao.addActionListener(acao);
        destino.add(botao);
        return botao;
    }

    // ============================================================

    protected JComboBox<String> criarComboBox(String[] itens, int x, int y, int w, int h) {
        JComboBox<String> combo = new JComboBox<>(itens);
        combo.setBounds(x, y, w, h);
        combo.setBackground(Color.WHITE);
        painel.add(combo);
        return combo;
    }

    protected void estilizar(JComponent componente, int tamanho, boolean negrito) {
        int estilo = negrito ? Font.BOLD : Font.PLAIN;
        componente.setFont(new Font("Arial", estilo, tamanho));
    }
}
