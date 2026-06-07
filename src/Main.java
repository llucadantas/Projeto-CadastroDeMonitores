import Interfaces.*;
import Model.*;
import Repository.AlunoRepositoryImp;
import Repository.CoordenadorRepositoryImp;
import Repository.DisciplinaRepositoryImp;
import Repository.EditalRepositoryImp;
import Service.*;
import Factory.AlunoFactory;
import Factory.CoordenadorFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // 1. Instanciando os Repositórios
    private static final AlunoRepository alunoRepo = new AlunoRepositoryImp();
    private static final CoordenadorRepository coordRepo = new CoordenadorRepositoryImp();
    private static final EditalRepository editalRepo = new EditalRepositoryImp();
    private static final DisciplinaRepository disciplinaRepo = new DisciplinaRepositoryImp();

    // 2. Instanciando as Fábricas (Factory Method)
    private static final PessoaFactory<Aluno> alunoFactory = new AlunoFactory();
    private static final PessoaFactory<Coordenador> coordFactory = new CoordenadorFactory();

    // 3. Instanciando os Serviços com Injeção de Dependência
    private static final CadastroInterface<Aluno> cadastroAluno = new CadastroAluno(alunoRepo, coordRepo);
    private static final CadastroInterface<Coordenador> cadastroCoordenador = new CadastroCoordenador(coordRepo, alunoRepo);

    // 4. Instanciando os Serviços de Login Separados (SRP Aplicado)
    private static final LoginInterface loginAluno = new LoginAluno(alunoRepo);
    private static final LoginInterface loginCoordenador = new LoginCoordenador(coordRepo);

    public static void main(String[] args) {
        int opcao = -1;

        System.out.println("=================================================");
        System.out.println(" Bem-vindo ao Sistema de Cadastro de Monitoria ");
        System.out.println("=================================================");

        while (opcao != 0) {
            System.out.println("\n[ MENU PRINCIPAL ]");
            System.out.println("1 - Fazer Login");
            System.out.println("2 - Cadastrar novo Aluno");
            System.out.println("3 - Cadastrar Coordenador");
            System.out.println("0 - Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        fazerLogin();
                        break;
                    case 2:
                        cadastrarAluno();
                        break;
                    case 3:
                        cadastrarCoordenador();
                        break;
                    case 0:
                        System.out.println("Sistema encerrado com sucesso. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // --- MÉTODOS DE LOGIN E CADASTRO REFATORADOS ---

    private static void fazerLogin() {
        System.out.println("\n--- TELA DE LOGIN ---");
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Eliminação do instanceof: Tentamos logar como Coordenador primeiro
        if (loginCoordenador.logar(email, senha)) {
            System.out.println("Login efetuado com sucesso como COORDENADOR!");
            menuCoordenador();
        }
        // Se falhar, tentamos logar como Aluno
        else if (loginAluno.logar(email, senha)) {
            System.out.println("Login efetuado com sucesso como ALUNO!");

            // O cast é seguro aqui pois sabemos que loginAluno retorna instâncias de Aluno
            Aluno alunoLogado = (Aluno) loginAluno.getUser();
            menuAluno(alunoLogado);
        }
        else {
            System.out.println("E-mail ou senha incorretos.");
        }
    }

    private static void cadastrarAluno() {
        System.out.println("\n--- CADASTRO DE ALUNO ---");
        try {
            System.out.print("Matrícula (7 dígitos): ");
            String matricula = scanner.nextLine();

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("E-mail: ");
            String email = scanner.nextLine();

            System.out.print("Senha (mínimo 7 caracteres): ");
            String senha = scanner.nextLine();

            Aluno a = alunoFactory.criarPessoa(nome, email, senha, matricula);

            cadastroAluno.cadastro(a);
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    private static void cadastrarCoordenador() {
        System.out.println("\n--- CADASTRO DE COORDENADOR ---");
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("E-mail: ");
            String email = scanner.nextLine();

            System.out.print("Senha (mínimo 7 caracteres): ");
            String senha = scanner.nextLine();

            Coordenador c = coordFactory.criarPessoa(nome, email, senha, null);

            cadastroCoordenador.cadastro(c);
            System.out.println("Coordenador cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        }
    }

    // --- MENUS ESPECÍFICOS POR PERFIL ---

    private static void menuCoordenador() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n[ PAINEL DO COORDENADOR ]");
            System.out.println("1 - Criar Edital de Monitoria");
            System.out.println("2 - Listar Editais");
            System.out.println("0 - Deslogar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        criarEdital();
                        break;
                    case 2:
                        listarEditais();
                        break;
                    case 0:
                        System.out.println("Deslogando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private static void menuAluno(Aluno aluno) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n[ PAINEL DO ALUNO - " + aluno.getNome() + " ]");
            System.out.println("1 - Listar Editais Abertos");
            System.out.println("2 - Inscrever-se em Monitoria");
            System.out.println("0 - Deslogar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        listarEditais();
                        break;
                    case 2:
                        inscreverEmMonitoria(aluno);
                        break;
                    case 0:
                        System.out.println("Deslogando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    // --- FUNCIONALIDADES DE EDITAL ---

    private static void criarEdital() {
        System.out.println("\n--- NOVO EDITAL DE MONITORIA ---");
        try {
            System.out.print("Título do Edital: ");
            String titulo = scanner.nextLine();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.print("Data de Início (dd/MM/yyyy): ");
            LocalDate dataInicio = LocalDate.parse(scanner.nextLine(), dtf);

            System.out.print("Data de Fim (dd/MM/yyyy): ");
            LocalDate dataFim = LocalDate.parse(scanner.nextLine(), dtf);

            System.out.print("Máximo de inscrições por aluno: ");
            int maxInscricoes = Integer.parseInt(scanner.nextLine());

            System.out.print("Nome da Disciplina contemplada: ");
            String nomeDisciplina = scanner.nextLine();
            System.out.print("Vagas Remuneradas: ");
            int vagasRem = Integer.parseInt(scanner.nextLine());
            System.out.print("Vagas Voluntárias: ");
            int vagasVol = Integer.parseInt(scanner.nextLine());

            Disciplina disciplina = new Disciplina(nomeDisciplina, vagasRem, vagasVol);
            disciplinaRepo.cadastrarDisciplina(disciplina);

            List<Disciplina> disciplinas = new ArrayList<>();
            disciplinas.add(disciplina);

            EditalDeMonitoria edital = new EditalDeMonitoria(
                    titulo, dataInicio, dataFim, maxInscricoes, 7.0, 7.0, disciplinas
            );

            editalRepo.cadastrarEdital(edital);
            System.out.println("Edital criado com sucesso! (ID: " + edital.getId() + ")");

        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
        } catch (Exception e) {
            System.out.println("Erro ao criar edital: " + e.getMessage());
        }
    }

    private static void listarEditais() {
        System.out.println("\n--- LISTA DE EDITAIS ---");
        List<EditalDeMonitoria> editais = editalRepo.listarEditais();

        if (editais.isEmpty()) {
            System.out.println("Nenhum edital cadastrado no sistema.");
            return;
        }

        for (EditalDeMonitoria edital : editais) {
            System.out.println(edital.toString());
            System.out.println("-------------------------");
        }
    }

    private static void inscreverEmMonitoria(Aluno aluno) {
        System.out.print("Digite o ID (UUID) do Edital que deseja se inscrever: ");
        try {
            // AJUSTE DE SEGURANÇA: Lê a linha inteira e converte para Long
            long idEdital = Long.parseLong(scanner.nextLine());

            EditalDeMonitoria edital = editalRepo.buscarEdital(idEdital);

            if (edital == null) {
                System.out.println("Edital não encontrado.");
                return;
            }

            System.out.print("Digite o nome da disciplina para a vaga: ");
            String nomeDisciplina = scanner.nextLine();

            boolean sucesso = edital.inscrever(aluno, nomeDisciplina);

            if (sucesso) {
                editalRepo.atualizarDados();
                System.out.println("Inscrição realizada com sucesso na disciplina " + nomeDisciplina + "!");
            } else {
                System.out.println("Não foi possível realizar a inscrição. Verifique se a disciplina existe, se o edital está aberto ou se você já está inscrito.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, digite um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao processar inscrição: " + e.getMessage());
        }
    }
}