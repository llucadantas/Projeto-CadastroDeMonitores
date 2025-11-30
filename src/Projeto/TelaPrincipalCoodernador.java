package Projeto;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TelaPrincipalCoodernador extends BaseTelas {


	private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JPasswordField txtSenha;


    public TelaPrincipalCoodernador() {
        // Define o tamanho da ÁREA DO FORMULÁRIO (o quadrado branco no meio)
        super("Principal", 450, 600);
 
        setTelaCheia(); 
    }

    @Override
    protected void montarTela() {
        // Título
        JLabel lblTitulo = criarLabel("TELA PRINCIPAL", 0, 15, 450, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        estilizar(lblTitulo, 22, true);

}}
