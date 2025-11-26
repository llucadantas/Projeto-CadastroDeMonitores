package Projeto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner leitor = new Scanner(System.in);
		Persistencia p = new Persistencia();
		boolean sair = false;

		System.out.println("Olá, Seja bem-vindo ao nosso sistema!");

		CentralDeInformacoes c = p.recuperarCentral();
		if (c == null) {
			c = new CentralDeInformacoes();
		}

		while (!sair) {

			System.out.println("\nQual opção deseja acessar ");
			System.out.println("1.Adicionar Aluno");
			System.out.println("2.Listar Alunos");
			System.out.println("3.Buscar aluno");
			System.out.println("4.Novo Edital");
			System.out.println("5.Listar Editais (Resumo)");
			System.out.println("6.Detalhar edital");
			System.out.println("7.Inscrever aluno em edital");
			System.out.println("8.Sair");
			String opc = leitor.nextLine();

			switch (opc) {

			case "1":
				System.out.println("Digite o nome do aluno: ");
				String nome = leitor.nextLine();

				System.out.println("Digite o sexo do aluno: Masculino/Feminino");
				String sexoS = leitor.nextLine().toLowerCase();

				SexoLista sexo;

				if (sexoS.equals("masculino")) {
					sexo = SexoLista.MASCULINO;
				} else {
					sexo = SexoLista.FEMININO;
				}

				System.out.println("Digite a matricula do aluno: ");
				String matricula = leitor.nextLine();

				System.out.println("Digite o email do aluno: ");
				String email = leitor.nextLine();

				System.out.println("Digite a senha do aluno: ");
				String senha = leitor.nextLine();

				Aluno aluno = new Aluno(nome, sexo, matricula, email, senha);

				if (c.adicionarAluno(aluno)) {
					p.salvarCentral(c);
					System.out.println("Foi adicionado com êxito.");
				} else {
					System.out.println("Falha");
				}
				break;

			case "2":
				System.out.println("\n--- Lista de Alunos ---");
				List<Aluno> alunos = c.getTodosAlunos();
				int i = 0;
				for (Aluno aluno1 : alunos) {
					i++;
					System.out.println(i + "º " + aluno1.toString());
				}
				if (i == 0)
					System.out.println("Nenhum aluno cadastrado.");
				System.out.println("------------------------");
				break;

			case "3":
				System.out.println("Digite o nome do aluno: ");
				String aluno1 = leitor.nextLine();
				List<Aluno> alunos1 = c.getTodosAlunos();
				boolean encontrado = false;

				for (Aluno a : alunos1) {
					if (a.getNome().equalsIgnoreCase(aluno1)) {
						System.out.println("Nome: " + a.getNome() + " Sexo: " + a.getSexo() + " Matricula: "
								+ a.getMatricula() + " Email: " + a.getEmail());
						encontrado = true;
						break;
					}
				}

				if (!encontrado) {
					System.out.println("Aluno não foi encontrado.");
				}
				break;

			case "4":
				try {
					System.out.println("Digite o titulo do edital: ");
					String titulo = leitor.nextLine();

					System.out.println("Digite a data de inicio (dd/MM/yyyy):");
					String dataInicio = leitor.nextLine();

					System.out.println("Digite a data de fim (dd/MM/yyyy):");
					String dataFim = leitor.nextLine();

					System.out.println("Este edital irá englobar quantas disciplinas:  ");
					int qntd = leitor.nextInt();
					leitor.nextLine();
					List<Disciplina> disciplinas1 = new ArrayList<>();

					for (int j = 0; j < qntd; j++) {
						System.out.println("Digite o nome da disciplina " + (j + 1) + ":");
						String nomeDisciplina = leitor.nextLine();
						System.out.println("Quantas vagas esta disciplina possui: ");
						int nVagas = leitor.nextInt();
						leitor.nextLine();
						Disciplina d = new Disciplina(nomeDisciplina, nVagas);
						disciplinas1.add(d);
					}

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate localDateInicio = LocalDate.parse(dataInicio, formatter);
					LocalDate localDateFim = LocalDate.parse(dataFim, formatter);

					EditalDeMonitoria edital = new EditalDeMonitoria(titulo, localDateInicio, localDateFim,
							disciplinas1);

					if (c.adicionarEdital(edital)) {
						p.salvarCentral(c);
						System.out.println("Edital adicionado com êxito.");
					} else {
						System.out.println("Falha na operação");
					}
				} catch (DateTimeParseException e) {
					System.out.println("Erro: Data em formato inválido. Use dd/MM/yyyy.");
				} catch (Exception e) {
					System.out.println("Erro ao criar edital. Tente novamente.");
					leitor.nextLine();
				}
				break;

			case "5":
				int quantidadeEditais = c.getTodosEditais().size();
				System.out.println("Há " + quantidadeEditais + " cadastrados.");
				break;
			case "6":
				try {
					System.out.println("Digite o ID do edital que deseja detalhar: ");
					long id1 = Long.parseLong(leitor.nextLine());

					EditalDeMonitoria editalEncontrado = c.recuperarEdital(id1);

					if (editalEncontrado != null) {
						System.out.println(editalEncontrado.toString());
					} else {
						System.out.println("❌ Edital não encontrado.");
					}
				} catch (NumberFormatException e) {
					System.out.println("❌ Erro: O ID deve ser um número.");
				}
				break;

			case "7":
				try {
					System.out.println("Digite a matricula do aluno que deseja inscrever: ");
					String nMatricula1 = leitor.nextLine();

					Aluno alunoParaInscrever = c.recuperarAlunoPorMatricula(nMatricula1);

					if (alunoParaInscrever == null) {
						System.out.println("Aluno não encontrado.");
						break;
					}

					System.out.println("Digite o ID do edital: ");
					long idLong = Long.parseLong(leitor.nextLine());

					EditalDeMonitoria editalParaInscrever = c.recuperarEdital(idLong);

					if (editalParaInscrever == null) {
						System.out.println("Edital não encontrado.");
						break;
					}

					System.out.println("Digite o nome da disciplina: ");
					String nDisciplina = leitor.nextLine();

					if (editalParaInscrever.inscrever(alunoParaInscrever, nDisciplina)) {
						p.salvarCentral(c);
						System.out.println("Inscrição realizada com sucesso.");
					} else {
						System.out.println(
								"Erro na inscrição. Verifique o prazo, a disciplina ou se o aluno já está inscrito.");
					}

				} catch (NumberFormatException e) {
					System.out.println("Erro: O ID ou a matrícula deve ser um número.");
				} catch (Exception e) {
					System.out.println("Erro inesperado na inscrição: " + e.getMessage());
				}
				break;

			case "8":
				p.salvarCentral(c);
				System.out.println("Programa encerrado");
				sair = true;
				break;

			default:
				System.out.println("Opção inválida.");
				break;

			}
		}
		leitor.close();
	}
}