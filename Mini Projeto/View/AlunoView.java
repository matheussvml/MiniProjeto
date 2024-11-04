package View;
import Model.Aluno;
import Controllers.AlunoController;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AlunoView {

    private final AlunoController alunoController = new AlunoController();
    private final Scanner scanner = new Scanner(System.in);

    public void listarAlunosHistoriaPresencial() {
        try {
            List<Aluno> alunos = alunoController.listarAlunosHistoriaPresencial();
            alunos.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Erro ao listar alunos de História (presencial): " + e.getMessage());
        }
    }


}

