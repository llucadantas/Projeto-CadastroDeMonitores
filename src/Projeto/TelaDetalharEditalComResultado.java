package Projeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaDetalharEditalComResultado extends BaseTelas {

    private EditalDeMonitoria edital; // Referência ao Edital
    private int idEdital;
    private boolean isCoordenador;
    private int idAluno;

    // O status do edital deve vir do objeto edital, não ser um booleano local
    // private boolean editalFechado = false; 

    private JComboBox<String> comboDisciplinas;
    private JButton botaoDesistir;
    private JButton botaoFecharEdital; // Referência para desabilitar, se necessário

    public TelaDetalharEditalComResultado(int idEdital, boolean isCoordenador, int idAluno) {
        // Obter o edital da Central/Banco de Dados
        this.edital = Central.getInstance().buscarEdital(idEdital);
        
        // Se o edital não for encontrado, podemos lançar um erro ou voltar,
        // mas para este contexto, assumimos que ele existe.
        if (this.edital == null) {
             throw new IllegalArgumentException("Edital não encontrado para o ID: " + idEdital);
        }
        
        super("Resultado do Edital: " + this.edital.getTitulo(), 650, 500);
        this.idEdital = idEdital;
        this.isCoordenador = isCoordenador;
        this.idAluno = idAluno;
    }

    @Override
    protected void montarTela() {
        // --- Título e Info do Edital ---
        criarLabel("RESULTADO DO EDITAL", 230, 20, 300, 30);
        criarLabel("Edital: " + edital.getTitulo(), 40, 50, 400, 25);
        
        // --- Seleção de Disciplina ---
        criarLabel("Selecione a disciplina:", 40, 90, 200, 25);
        
        // 1. Coleta os nomes das disciplinas do objeto Edital
        List<Disciplina> disciplinasDoEdital = edital.getDisciplinas();
        String[] nomesDisciplinas = disciplinasDoEdital.stream()
                                                      .map(Disciplina::getNome)
                                                      .toArray(String[]::new);
        
        // 2. Cria o ComboBox com as disciplinas reais
        comboDisciplinas = criarComboBox(
                nomesDisciplinas,
                40, 120, 300, 30
        );

        criarBotao("Ver Ranking/Resultado", 360, 120, 200, 30,
            e -> mostrarRanking(comboDisciplinas.getSelectedItem().toString())
        );

        // =====================================================================
        // BOTÃO DESISTIR – SOMENTE PARA ALUNO
        // =====================================================================
        if (!isCoordenador) {
            botaoDesistir = criarBotao(
                    "Desistir da Inscrição",
                    200, 350, 250, 40,
                    e -> desistir(comboDisciplinas.getSelectedItem().toString())
            );
            botaoDesistir.setVisible(false); // Escondido inicialmente
        }

        // =====================================================================
        // BOTÃO FECHAR EDITAL – SOMENTE COORDENADOR
        // =====================================================================
        if (isCoordenador) {
            botaoFecharEdital = criarBotao("Fechar Edital", 230, 400, 180, 40, e -> fecharEdital());
            // Se o edital já estiver fechado, desabilita o botão
            if (Central.getInstance().isEditalFechado(idEdital)) {
                 botaoFecharEdital.setEnabled(false);
                 botaoFecharEdital.setText("Edital JÁ FECHADO");
            }
        }
    }

    private void mostrarRanking(String nomeDisciplina) {
        
        // 🚨 TO-DO: Implementar a exibição do ranking (tabela)
        
        boolean isFechado = Central.getInstance().isEditalFechado(idEdital);

        JOptionPane.showMessageDialog(this,
                "Ranking da disciplina: " + nomeDisciplina +
                        "\n(Exibir tabela de Aluno, CRE, Média, Nota Final, Status aqui)\n" +
                        (isFechado ? "\n*Resultado Final Oficial*" : "\n*Resultado Parcial*"),
                "Ranking do Edital",
                JOptionPane.INFORMATION_MESSAGE
        );

        // SE ALUNO – MOSTRAR BOTÃO DESISTIR (Apenas se o edital não estiver fechado)
        if (!isCoordenador && !isFechado) {
            botaoDesistir.setVisible(true);
        } else if (!isCoordenador && isFechado) {
            // Se o aluno entrar no resultado final, o botão não deve aparecer
            if (botaoDesistir != null) {
                botaoDesistir.setVisible(false);
            }
        }
    }

    // =====================================================================
    // FUNÇÃO: DESISTIR DA INSCRIÇÃO
    // =====================================================================
    private void desistir(String nomeDisciplina) {

        if (Central.getInstance().isEditalFechado(idEdital)) {
            JOptionPane.showMessageDialog(this,
                    "O edital já está fechado.\nNão é mais possível desistir.",
                    "Operação não permitida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- Verifica se o aluno está inscrito ---
        // Assumindo que o método da Central verifica a inscrição pelo ID do Aluno e ID do Edital/Disciplina
        boolean inscrito = Central.getInstance().isAlunoInscrito(idAluno, idEdital, nomeDisciplina);

        if (!inscrito) {
            JOptionPane.showMessageDialog(this,
                    "Você não está inscrito nesta disciplina.",
                    "Não inscrito",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmação
        int opc = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja desistir da inscrição na disciplina " + nomeDisciplina + "?",
                "Confirmar desistência",
                JOptionPane.YES_NO_OPTION
        );

        if (opc != JOptionPane.YES_OPTION) return;

        // --- Realiza a desistência e verifica se a vaga ficou aberta ---
        // O método da Central deve tratar a remoção do aluno e retornar se o ranking precisa ser atualizado
        boolean sucesso = Central.getInstance().desistirInscricao(idAluno, idEdital, nomeDisciplina);

        if (sucesso) {
            // O recálculo deve ser feito internamente após a desistência
            Central.getInstance().recalcularResultadoEdital(idEdital);
            
            // Esconde o botão após a desistência
            if (botaoDesistir != null) {
                botaoDesistir.setVisible(false); 
            }
            
            JOptionPane.showMessageDialog(
                    this,
                    "Desistência realizada com sucesso!\n" +
                            "O ranking será recalculado para a disciplina.",
                    "Concluído",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
             JOptionPane.showMessageDialog(
                    this,
                    "Falha ao processar a desistência.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================================
    // FECHAR EDITAL (COORDENADOR)
    // =====================================================================
    private void fecharEdital() {

        int opc = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja FECHAR este Edital?\n" +
                "Isto registrará o resultado atual como FINAL e impedirá desistências futuras.",
                "Confirmar Fechamento do Edital",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opc != JOptionPane.YES_OPTION) return;

        // Chama o método da Central para fechar o Edital e registrar o resultado final
        central.getInstance().fecharEdital(idEdital);

        // Desabilita o botão após fechar
        if (botaoFecharEdital != null) {
            botaoFecharEdital.setEnabled(false);
            botaoFecharEdital.setText("Edital JÁ FECHADO");
        }

        JOptionPane.showMessageDialog(
                this,
                "Edital fechado com sucesso!\n\n" +
                        "O resultado atual foi registrado como RESULTADO FINAL.",
                "Edital Encerrado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}