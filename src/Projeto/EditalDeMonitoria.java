package Projeto;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class EditalDeMonitoria {
	private String nEdital;
	private String titulo;
	private LocalDate inicioInscricoes;
	private LocalDate fimInscricoes;
	private List<Disciplina> disciplinas = new ArrayList<>();
	private long id = System.currentTimeMillis();
	
	public EditalDeMonitoria(String nEdital, LocalDate inicioInscricoes, LocalDate fimInscricoes, List<Disciplina> disciplinas) {
		this.nEdital = nEdital;
		this.inicioInscricoes = inicioInscricoes;
		this.fimInscricoes = fimInscricoes;
		this.disciplinas = disciplinas;
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
		LocalDate hoje = LocalDate.now();
		Period prazo = Period.between(hoje, fimInscricoes);
		if(prazo.getDays()<=0) {
			return true;
		}
		return false;
		
	}
	

	public Object[] toObjectArray() {
        String status;
        if (jaAcabou()) {
            status = "Encerrado";
        } else if (LocalDate.now().isBefore(inicioInscricoes)) {
            status = "Aguardando Abertura";
        } else {
            status = "Aberto";
        }
        
        return new Object[]{id, titulo, inicioInscricoes, fimInscricoes, status};
    }

	public String toString() {
		String titulo = "- Edital de monitorial " + id + " - \n";
		String dStr = "";
		String status = "";
		for(Disciplina d: disciplinas) {
			dStr += d.getNome() + " - " + d.getnVagas() + " Vagas - \n"; 
		}
		if (!jaAcabou()) {
			status = "abertas";
		}
		else {
			status = "encerradas";
		}
		
		return titulo + dStr + "Inscrições " + status;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getnEdital() {
		return nEdital;
	}

	public void setnEdital(String nEdital) {
		this.nEdital = nEdital;
	}

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

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	
}
