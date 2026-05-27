package Model;

import Interfaces.EstadoEdital;
import Model.State.EditalAberto;
import Model.State.EditalAguardando;
import Model.State.EditalFechado;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditalDeMonitoria {
    private String titulo;
    private LocalDate inicioInscricoes;
    private LocalDate fimInscricoes;
    private int maximoInscricoes;
    private double pesoCRE;
    private double pesoMedia;
    private EstadoEdital estado;
    private List<Disciplina> disciplinas = new ArrayList<>();
    private long id = System.currentTimeMillis() % 1000000;

    public EditalDeMonitoria(String titulo, LocalDate inicioInscricoes, LocalDate fimInscricoes, int maximoInscricoes, double pesoCRE, double pesoMedia, List<Disciplina> disciplinas) {
        this.titulo = titulo;
        this.inicioInscricoes = inicioInscricoes;
        this.fimInscricoes = fimInscricoes;
        this.maximoInscricoes = maximoInscricoes;
        this.pesoCRE = pesoCRE;
        this.pesoMedia = pesoMedia;
        this.disciplinas = disciplinas;
        atualizarEstado();
    }

    public void atualizarEstado() {

        ChronoLocalDate hoje = LocalDate.now();

        if(hoje.isAfter(hoje)) {
            estado = new EditalFechado();
        }
        else if(hoje.isAfter(inicioInscricoes)) {
            estado = new EditalAguardando();
        }
        else {
            estado = new EditalAberto();
        }
    }

    public boolean inscrever(Aluno aluno, String nDisciplina) {
        atualizarEstado();
        return estado.permitirInscricao(this, aluno, nDisciplina);
    }

    @Override
    public String toString() {
        String tituloHeader = "- Edital de monitoria " + id + " - \n";

        StringBuilder dStr = new StringBuilder();

        for(Disciplina d: disciplinas) {
            dStr.append(d.getNome()).append(" - Vagas - \n");
        }

        String status = getEstado().getNomeEstado();

        return tituloHeader + dStr.toString() + "Inscrições " + status;
    }

    public EstadoEdital getEstado() {
        atualizarEstado();
        return estado;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getMaximoInscricoes() {
        return maximoInscricoes;
    }


    public double getPesoCRE() {
        return pesoCRE;
    }


    public double getPesoMedia() {
        return pesoMedia;
    }


    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

}