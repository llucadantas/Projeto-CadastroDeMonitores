/**
 * A classe {@code CentralDeInformacoes} atua como o repositório central de dados do sistema.
 * <p>
 * Ela é responsável por armazenar e gerenciar todas as coleções de objetos essenciais,
 * como Alunos, Editais de Monitoria e o Coordenador.
 * </p>
 * * @author [Seu Nome ou Nome do Grupo]
 * @version 1.0
 */
package Projeto;
import java.util.ArrayList;
import java.util.List;

public class CentralDeInformacoes {
    
    /** Lista contendo todos os objetos {@link Aluno} cadastrados no sistema. */
	private List<Aluno> todosAlunos = new ArrayList<>();
    
    /** Lista contendo todos os objetos {@link EditalDeMonitoria} cadastrados. */
	private List<EditalDeMonitoria> todosEditais = new ArrayList<>();
    
    /** O objeto {@link Coordenador} principal do sistema. */
	private Coordenador coordenador = null;
	
    // --- CONSTRUTOR ---
    
    /**
     * Construtor padrão da Central de Informações.
     * Inicializa as coleções de dados como listas vazias.
     */
    public CentralDeInformacoes() {
        // As listas já são inicializadas na declaração do campo.
    }
    
    // --- MÉTODOS DE COORDENADOR ---

    /**
     * Retorna o objeto {@link Coordenador} principal.
     * * @return O objeto Coordenador, ou {@code null} se não estiver cadastrado.
     */
	public Coordenador getCoordenador() {
		return coordenador;
	}

    /**
     * Define o objeto {@link Coordenador} principal.
     * * @param coordenador O objeto Coordenador a ser definido.
     */
	public void setCoodernador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

    // --- MÉTODOS DE ALUNO ---

    /**
     * Adiciona um novo aluno à lista, verificando duplicidade pela matrícula.
     * * @param aluno O objeto {@link Aluno} a ser adicionado.
     * @return {@code true} se o aluno foi adicionado; {@code false} se a matrícula já existe.
     */
	public boolean adicionarAluno(Aluno aluno) {
		for(Aluno aluno1: todosAlunos) {
			if(aluno1.getMatricula().equals(aluno.getMatricula())) {
				
				return false;
			}
		}
		return todosAlunos.add(aluno);
	}

    /**
     * Retorna a lista de todos os {@link Aluno}s cadastrados.
     * * @return Uma {@code List} de todos os alunos.
     */
	public List<Aluno> getTodosAlunos() {
		return todosAlunos;
	}

    /**
     * Substitui a lista completa de alunos pela lista fornecida.
     * * @param todosAlunos A nova lista de {@link Aluno}s.
     */
	public void setTodosAlunos(List<Aluno> todosAlunos) {
		this.todosAlunos = todosAlunos;
	}
	
    /**
     * Busca um aluno pela matrícula.
     * * @param nMatricula A matrícula do aluno a ser buscado.
     * @return O objeto {@link Aluno} encontrado, ou {@code null} se não for encontrado.
     */
	public Aluno recuperarAlunoPorMatricula(String nMatricula) {
		for(Aluno aluno: todosAlunos) {
			if(aluno.getMatricula().equals(nMatricula)) {
				return aluno;
			}
		}
		return null;
	}
	
    /**
     * Busca um aluno pelo e-mail.
     * * @param nEmail O e-mail do aluno a ser buscado.
     * @return O objeto {@link Aluno} encontrado, ou {@code null} se não for encontrado.
     */
	public Aluno recuperarAlunoPorEmail(String nEmail) {
		for(Aluno aluno: todosAlunos) {
			if(aluno.getEmail().equals(nEmail)) {
				return aluno;
			}
		}
		return null;
	}
	
    // --- MÉTODOS DE EDITAL ---

    /**
     * Retorna a lista de todos os {@link EditalDeMonitoria} cadastrados.
     * * @return Uma {@code List} de todos os editais.
     */
	public List<EditalDeMonitoria> getTodosEditais() {
		return todosEditais;
	}

    /**
     * Substitui a lista completa de editais pela lista fornecida.
     * * @param todosEditais A nova lista de {@link EditalDeMonitoria}.
     */
	public void setTodosEditais(List<EditalDeMonitoria> todosEditais) {
		this.todosEditais = todosEditais;
	}

    /**
     * Adiciona um novo edital, verificando duplicidade pelo ID.
     * * @param edital O objeto {@link EditalDeMonitoria} a ser adicionado.
     * @return {@code true} se o edital foi adicionado; {@code false} se o ID já existe.
     */
	public boolean adicionarEdital(EditalDeMonitoria edital) {
		
		for(EditalDeMonitoria e: todosEditais) {
			if(e.getId() == edital.getId()) {
				return false;
			}
		}
		return todosEditais.add(edital);
	}
	
    /**
     * Busca um edital pelo seu ID.
     * * @param id O ID do edital (tipo long).
     * @return O objeto {@link EditalDeMonitoria} encontrado, ou {@code null}.
     */
	public EditalDeMonitoria recuperarEdital(long id) {
		for(EditalDeMonitoria e: todosEditais) {
			if(e.getId() == id) {
				return e;
			}
		}
		return null;
	}
	
    /**
     * Retorna os editais em que um aluno está inscrito para um determinado ID de edital.
     * Nota: A lógica interna deste método requer a iteração sobre disciplinas e alunos.
     * * @param matriula1 A matrícula do aluno (String).
     * @param id1 O ID do edital a ser pesquisado (long).
     * @return Uma {@code List} de editais inscritos.
     */
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