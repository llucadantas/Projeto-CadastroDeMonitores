package Model;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Disciplina {
	private String nome;
	private int numeroVagasRemanescentes;
	private int numeroVagasVoluntarias;
	private List<Aluno> alunos = new ArrayList<>();

	// Construtor vazio necessário para frameworks (JPA/Hibernate, Jackson, etc)
	public Disciplina() {
	}

	public Disciplina(String nome, int numeroVagasRemanescentes, int numeroVagasVoluntarias) {
		this.nome = nome;
		this.numeroVagasRemanescentes = numeroVagasRemanescentes;
		this.numeroVagasVoluntarias = numeroVagasVoluntarias;
	}

	// Retorna uma cópia somente leitura para proteger o encapsulamento
	public List<Aluno> getAlunos() {
		return Collections.unmodifiableList(alunos);
	}

	public boolean adicionarAluno(Aluno aluno) {
		// Opcional: usar Streams para deixar mais limpo
		boolean jaMatriculado = alunos.stream()
				.anyMatch(a -> a.getMatricula().equals(aluno.getMatricula()));

		if (jaMatriculado) {
			return false;
		}

		return alunos.add(aluno);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumeroVagasRemanescentes() {
		return numeroVagasRemanescentes;
	}

	public void setNumeroVagasRemanescentes(int numeroVagasRemanescentes) {
		this.numeroVagasRemanescentes = numeroVagasRemanescentes;
	}

	public int getNumeroVagasVoluntarias() {
		return numeroVagasVoluntarias;
	}

	public void setNumeroVagasVoluntarias(int numeroVagasVoluntarias) {
		this.numeroVagasVoluntarias = numeroVagasVoluntarias;
	}
}
