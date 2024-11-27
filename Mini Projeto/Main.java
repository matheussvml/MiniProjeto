import Controllers.AlunoController;
import Controllers.DisciplinaController;
import Controllers.BibliotecaController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String DisciplinasURL = "https://sswfuybfs8.execute-api.us-east-2.amazonaws.com/disciplinaServico/msDisciplina";
        String AlunosURL = "https://rmi6vdpsq8.execute-api.us-east-2.amazonaws.com/msAluno";
        String BibliotecaURL = "https://qiiw8bgxka.execute-api.us-east-2.amazonaws.com/acervo/biblioteca";

        AlunoController alunoController = new AlunoController(AlunosURL);
        DisciplinaController disciplinaController = new DisciplinaController(DisciplinasURL);
        BibliotecaController bibliotecaController = new BibliotecaController(BibliotecaURL);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Liste os alunos do curso de História na modalidade Presencial");
            System.out.println("2. Busque um aluno por ID ou nome");
            System.out.println("3. Liste todas as disciplinas disponíveis");
            System.out.println("4. Matricule um aluno em uma disciplina");
            System.out.println("5. Liste todas as disciplinas matriculadas de um aluno");
            System.out.println("6. Remova uma disciplina de um aluno");
            System.out.println("7. Reserve um livro");
            System.out.println("8. Liste todos os livros reservados por um aluno");
            System.out.println("9. Cancele uma reserva de livro");
            System.out.println("0. Fechar");

            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    alunoController.listarAlunosHistoria();
                    break;
                case "2":
                    System.out.print("Digite 1 para buscar por nome ou 2 para buscar por ID: ");
                    int escolha = Integer.parseInt(scanner.nextLine());
                    if (escolha == 1) {
                        System.out.print("Digite o nome do aluno: ");
                        alunoController.buscarAluno(null, scanner.nextLine());
                    } else if (escolha == 2) {
                        System.out.print("Digite o ID do aluno: ");
                        alunoController.buscarAluno(scanner.nextLine(), null);
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;
                case "3":
                    disciplinaController.exibirDisciplinas();
                    break;
                case "4":
                    System.out.print("Digite o ID do aluno: ");
                    String alunoId = scanner.nextLine();
                    System.out.print("Digite o ID da disciplina: ");
                    disciplinaController.matricularDisciplina(alunoId, scanner.nextLine());
                    break;
                case "5":
                    System.out.print("Digite o ID do aluno: ");
                    disciplinaController.listarDisciplinasMatriculadas(scanner.nextLine());
                    break;
                case "6":
                    System.out.print("Digite o ID do aluno: ");
                    alunoId = scanner.nextLine();
                    System.out.print("Digite o ID da disciplina: ");
                    disciplinaController.removerDisciplina(alunoId, scanner.nextLine());
                    break;
                case "7":
                    System.out.print("Digite o ID do aluno: ");
                    alunoId = scanner.nextLine();
                    System.out.print("Digite o ID do livro: ");
                    bibliotecaController.reservarLivro(alunoId, scanner.nextLine());
                    break;
                case "8":
                    System.out.print("Digite o ID do aluno: ");
                    bibliotecaController.listarLivrosReservados(scanner.nextLine());
                    break;
                case "9":
                    System.out.print("Digite o ID do aluno: ");
                    alunoId = scanner.nextLine();
                    System.out.print("Digite o ID do livro: ");
                    bibliotecaController.cancelarReserva(alunoId, scanner.nextLine());
                    break;
                case "0":
                    System.out.println("Encerrando o programa.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
