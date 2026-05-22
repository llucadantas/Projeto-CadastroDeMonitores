import Model.Aluno;
import Model.Disciplina;
import Model.EditalDeMonitoria;
import Repository.AlunoRepositoryImp;
import Repository.CoordenadorRepositoryImp;
import Repository.DisciplinaRepositoryImp;
import Repository.EditalRepositoryImp;
import Service.Cadastro;
import Service.Login;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // 1. Instanciando os Repositórios
    private static final AlunoRepositoryImp alunoRepo = new AlunoRepositoryImp();
    private static final CoordenadorRepositoryImp coordRepo = new CoordenadorRepositoryImp();
    private static final EditalRepositoryImp editalRepo = new EditalRepositoryImp();
    private static final DisciplinaRepositoryImp disciplinaRepo = new DisciplinaRepositoryImp();

    // 2. Instanciando os Serviços com Injeção de Dependência (passando os repositórios)
    private static final Cadastro cadastro = new Cadastro(alunoRepo, coordRepo);
    private static final Login login = new Login(alunoRepo, coordRepo);

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

    // --- MÉTODOS DE LOGIN E CADASTRO ---

    private static void fazerLogin() {
        System.out.println("\n--- TELA DE LOGIN ---");
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        // Usa o serviço de Login atualizado
        if (login.loginCoodernador(email, senha)) {
            System.out.println("Login efetuado com sucesso como COORDENADOR!");
            menuCoordenador();
        } else if (login.login(email, senha)) {
            System.out.println("Login efetuado com sucesso como ALUNO!");
            // Pega o aluno que foi salvo no estado interno da classe Login
            Aluno alunoLogado = login.getUser();
            menuAluno(alunoLogado);
        } else {
            System.out.println("E-mail ou senha incorretos.");
        }
    }

    private static void cadastrarAluno() {
        System.out.println("\n--- CADASTRO DE ALUNO ---");
        try {
            System.out.print("Matrícula (7 dígitos): ");
            String matricula = scanner.nextLine();
            // Validacao.matriculaInvalida(matricula); // Descomente se ainda tiver a classe Validacao independente de Central

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            // Validacao.isEmailValido(email);

            System.out.print("Senha (mínimo 7 caracteres): ");
            String senha = scanner.nextLine();
            // Validacao.validacaoSenha(senha);

            // Chama o serviço de cadastro, que já faz a validação de duplicidade e salva no XML
            cadastro.cadastrarAluno(matricula, senha, nome, email);
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

            // Chama o serviço de cadastro
            cadastro.cadastrarCoordenador(senha, nome, email);
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

            // Usando a API de datas do Java 8+
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

            // Cadastra a disciplina isoladamente no repositório dela
            Disciplina disciplina = new Disciplina(nomeDisciplina, vagasRem, vagasVol);
            disciplinaRepo.cadastrarDisciplina(disciplina);

            List<Disciplina> disciplinas = new ArrayList<>();
            disciplinas.add(disciplina);

            // Cria o edital com a disciplina vinculada
            EditalDeMonitoria edital = new EditalDeMonitoria(
                    titulo, dataInicio, dataFim, maxInscricoes, 7.0, 7.0, disciplinas
            );

            // Salva o edital (o repositório cuida do XML automaticamente)
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
            // O ID do edital agora é uma String (UUID)
            long idEdital = scanner.nextLong();
            EditalDeMonitoria edital = editalRepo.buscarEdital(idEdital);

            if (edital == null) {
                System.out.println("Edital não encontrado.");
                return;
            }

            System.out.print("Digite o nome da disciplina para a vaga: ");
            String nomeDisciplina = scanner.nextLine();

            // Tenta inscrever o aluno
            boolean sucesso = edital.inscrever(aluno, nomeDisciplina);

            if (sucesso) {
                // Muito importante: Como o estado do edital mudou (recebeu um aluno novo),
                // forçamos o repositório a reescrever o XML.
                editalRepo.atualizarDados();
                System.out.println("Inscrição realizada com sucesso na disciplina " + nomeDisciplina + "!");
            } else {
                System.out.println("Não foi possível realizar a inscrição. Verifique se a disciplina existe, se o edital está aberto ou se você já está inscrito.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar inscrição: " + e.getMessage());
        }
    }
}