package Model;

import Interfaces.EstadoEdital;
import Model.State.EditalAberto;
import Model.State.EditalAguardando;
import Model.State.EditalFechado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditalDeMonitoria {
    private String titulo;
    private Date inicioInscricoes;
    private Date fimInscricoes;
    private int maximoInscricoes;
    private double pesoCRE;
    private double pesoMedia;
    private EstadoEdital estado;
    private List<Disciplina> disciplinas = new ArrayList<>();
    private long id = System.currentTimeMillis() % 1000000;

    public EditalDeMonitoria(String titulo, Date inicioInscricoes, Date fimInscricoes, int maximoInscricoes, double pesoCRE, double pesoMedia, List<Disciplina> disciplinas) {
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

        Date hoje = new Date();

        if(hoje.after(fimInscricoes)) {
            estado = new EditalFechado();
        }
        else if(hoje.before(inicioInscricoes)) {
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getInicioInscricoes() {
        return inicioInscricoes;
    }

    public void setInicioInscricoes(Date inicioInscricoes) {
        this.inicioInscricoes = inicioInscricoes;
    }

    public Date getFimInscricoes() {
        return fimInscricoes;
    }

    public void setFimInscricoes(Date fimInscricoes) {
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