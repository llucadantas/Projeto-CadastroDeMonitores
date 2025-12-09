package Projeto;

import java.text.SimpleDateFormat;
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
    private List<Disciplina> disciplinas = new ArrayList<>();
    private long id = System.currentTimeMillis() % 1000000;
    private String status;
    public EditalDeMonitoria(String titulo, Date inicioInscricoes, Date fimInscricoes, int maximoInscricoes, double pesoCRE, double pesoMedia, List<Disciplina> disciplinas) {
        this.titulo = titulo;
        this.inicioInscricoes = inicioInscricoes;
        this.fimInscricoes = fimInscricoes;
        this.maximoInscricoes = maximoInscricoes;
        this.pesoCRE = pesoCRE;
        this.pesoMedia = pesoMedia;
        this.disciplinas = disciplinas;
        if(jaAcabou()) {
        	this.status = "Encerrado";
        }
        else {
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
        Date hoje = new Date();
        return hoje.after(fimInscricoes);
    }
    
    public Object[] toObjectArray() {
        String status;
        Date hoje = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (jaAcabou()) {
            status = "Encerrado";
        } else if (hoje.before(inicioInscricoes)) {
            status = "Aguardando Abertura";
        } else {
            status = "Aberto";
        }
        
        return new Object[]{id, titulo, sdf.format(inicioInscricoes), sdf.format(fimInscricoes), status};
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