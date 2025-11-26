package Projeto;
import java.util.ArrayList;
import java.util.List;

public class CentralDeInformacoes {
	private List<Aluno> todosAlunos = new ArrayList<>();
	private List<EditalDeMonitoria> todosEditais = new ArrayList<>();
	
	
	public boolean adicionarAluno(Aluno aluno) {
		for(Aluno aluno1: todosAlunos) {
			if(aluno1.getMatricula().equals(aluno.getMatricula())) {
				
				return false;
			}
		}
		return todosAlunos.add(aluno);
	}

	public List<Aluno> getTodosAlunos() {
		return todosAlunos;
	}

	public void setTodosAlunos(List<Aluno> todosAlunos) {
		this.todosAlunos = todosAlunos;
	}
	
	public Aluno recuperarAlunoPorMatricula(String nMatricula) {
		for(Aluno aluno: todosAlunos) {
			if(aluno.getMatricula().equals(nMatricula)) {
				return aluno;
			}
		}
		return null;
	}
	
	public List<EditalDeMonitoria> getTodosEditais() {
		return todosEditais;
	}

	public void setTodosEditais(List<EditalDeMonitoria> todosEditais) {
		this.todosEditais = todosEditais;
	}

	public boolean adicionarEdital(EditalDeMonitoria edital) {
		
		for(EditalDeMonitoria e: todosEditais) {
			if(e.getId() == edital.getId()) {
				return false;
			}
		}
		return todosEditais.add(edital);
	}
	
	public EditalDeMonitoria recuperarEdital(long id) {
		for(EditalDeMonitoria e: todosEditais) {
			if(e.getId() == id) {
				return e;
			}
		}
		return null;
	}
	
	public List<EditalDeMonitoria> recuperarInscricoes(String matriula1, long id1) {
		List<EditalDeMonitoria> editaisInscritos = new ArrayList<>();
		for(EditalDeMonitoria e: todosEditais) {
			if(e.getId() == id1) {
				for(Disciplina d: e.getDisciplinas()) {
					for(Aluno a: d.getAlunos()) {
						if(a.getMatricula().equals(matriula1)) {
							editaisInscritos.add(e);
						}
					}
				}
			}
		}
		return editaisInscritos;
	}
}
		
		
	
	
