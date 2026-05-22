package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EditalDeMonitoria {

    private String titulo;
    private LocalDate inicioInscricoes;
    private LocalDate fimInscricoes;
    private int maximoInscricoes;
    private double pesoCRE;
    private double pesoMedia;
    private List<Disciplina> disciplinas = new ArrayList<>();
    private long id = System.currentTimeMillis() % 1000000;
    private String status;

    public EditalDeMonitoria(String titulo, LocalDate inicioInscricoes, LocalDate fimInscricoes, int maximoInscricoes, double pesoCRE, double pesoMedia, List<Disciplina> disciplinas) {
        this.titulo = titulo;
        this.inicioInscricoes = inicioInscricoes;
        this.fimInscricoes = fimInscricoes;
        this.maximoInscricoes = maximoInscricoes;
        this.pesoCRE = pesoCRE;
        this.pesoMedia = pesoMedia;
        this.disciplinas = disciplinas;
        if(jaAcabou()) {
            this.status = "Encerrado";
        } else {
            this.status = "Aberto";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean inscrever(Aluno aluno, String nDisciplina) {
        if(jaAcabou()) {
            return false;
        }

        for(Disciplina d: disciplinas) {
            if(d.getNome().equalsIgnoreCase(nDisciplina)) {
                return d.adicionarAluno(aluno);
            }
        }
        return false;
    }

    public boolean jaAcabou() {
        // CORREÇÃO: Usar LocalDate.now() e isAfter()
        LocalDate hoje = LocalDate.now();
        return hoje.isAfter(fimInscricoes);
    }

    public Object[] toObjectArray() {
        String status;

        // CORREÇÃO: Usar LocalDate e DateTimeFormatter
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (jaAcabou()) {
            status = "Encerrado";
        } else if (hoje.isBefore(inicioInscricoes)) { // CORREÇÃO: isBefore()
            status = "Aguardando Abertura";
        } else {
            status = "Aberto";
        }

        // CORREÇÃO: Formatar as datas usando a nova API
        return new Object[]{id, titulo, inicioInscricoes.format(formatter), fimInscricoes.format(formatter), status};
    }

    @Override
    public String toString() {
        String tituloHeader = "- Edital de monitoria " + id + " - \n";

        StringBuilder dStr = new StringBuilder();
        String status;

        for(Disciplina d: disciplinas) {
            dStr.append(d.getNome()).append(" - Vagas - \n");
        }

        if (!jaAcabou()) {
            status = "abertas";
        } else {
            status = "encerradas";
        }

        return tituloHeader + dStr.toString() + "Inscrições " + status;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // CORREÇÃO: Os getters e setters agora retornam e recebem LocalDate
    public LocalDate getInicioInscricoes() {
        return inicioInscricoes;
    }

    public void setInicioInscricoes(LocalDate inicioInscricoes) {
        this.inicioInscricoes = inicioInscricoes;
    }

    public LocalDate getFimInscricoes() {
        return fimInscricoes;
    }

    public void setFimInscricoes(LocalDate fimInscricoes) {
        this.fimInscricoes = fimInscricoes;
    }

    public int getMaximoInscricoes() {
        return maximoInscricoes;
    }

    public void setMaximoInscricoes(int maximoInscricoes) {
        this.maximoInscricoes = maximoInscricoes;
    }

    public double getPesoCRE() {
        return pesoCRE;
    }

    public void setPesoCRE(double pesoCRE) {
        this.pesoCRE = pesoCRE;
    }

    public double getPesoMedia() {
        return pesoMedia;
    }

    public void setPesoMedia(double pesoMedia) {
        this.pesoMedia = pesoMedia;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}