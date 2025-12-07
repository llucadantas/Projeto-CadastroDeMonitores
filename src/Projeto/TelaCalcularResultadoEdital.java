package Projeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCalcularResultadoEdital extends BaseTelas {

    private int idEdital;

    public TelaCalcularResultadoEdital(int idEdital) {
        super("Calcular Resultado do Edital", 500, 300);
        this.idEdital = idEdital;
    }

    @Override
    protected void montarTela() {

        criarLabel("CALCULAR RESULTADO DO EDITAL", 100, 30, 350, 30);

        criarLabel("Ao confirmar, o sistema irá:", 40, 90, 400, 25);
        criarLabel("- Gerar ranking por disciplina", 60, 120, 300, 25);
        criarLabel("- Marcar contemplados", 60, 140, 300, 25);
        criarLabel("- Habilitar visualização aos alunos", 60, 160, 350, 25);

        criarBotao("Calcular Resultado", 140, 210, 200, 35, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularResultado();
            }
        });
    }

    private void calcularResultado() {
        // Aqui você chama o serviço/DAO real
        // Ex: ResultadoController.calcularResultado(idEdital);

        JOptionPane.showMessageDialog(this,
                "Resultado do edital calculado com sucesso!\nAgora os alunos já podem visualizar o ranking.",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }
}
