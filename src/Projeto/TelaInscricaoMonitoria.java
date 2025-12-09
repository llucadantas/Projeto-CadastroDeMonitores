package Projeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaInscricaoMonitoria extends BaseTelas {

    private JComboBox<String> comboDisciplinas;
    private JTextField campoCRE;
    private JTextField campoMedia;

    private int idEdital; 
    private List<String> disciplinas; 

    public TelaInscricaoMonitoria(int idEdital, List<String> disciplinas) {
        super("Inscrição em Monitoria", 450, 360);
        this.idEdital = idEdital;
        this.disciplinas = disciplinas;
    }

    @Override
    protected void montarTela() {

        criarLabel("INSCRIÇÃO EM MONITORIA", 120, 20, 250, 30);
        
        criarLabel("Selecione a Disciplina:", 40, 80, 200, 25);
        comboDisciplinas = criarComboBox(
            disciplinas.toArray(new String[0]),
            40, 110, 350, 30
        );

        criarLabel("Seu CRE Atual:", 40, 160, 200, 25);
        campoCRE = criarCampoTexto(40, 190, 150, 30);

        criarLabel("Sua Média na Disciplina:", 230, 160, 200, 25);
        campoMedia = criarCampoTexto(230, 190, 150, 30);

        criarBotao("Enviar Inscrição", 110, 250, 220, 35, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarInscricao();
            }
        });
    }

    private void enviarInscricao() {
        String disciplina = comboDisciplinas.getSelectedItem().toString();
        String cre = campoCRE.getText();
        String media = campoMedia.getText();

        if (cre.isEmpty() || media.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
            "Inscrição enviada com sucesso!\n" +
            "Edital: " + idEdital + "\n" +
            "Disciplina: " + disciplina + "\n" +
            "CRE: " + cre + "\n" +
            "Média: " + media,
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }
}
