package View;
import Controllers.DisciplinaController;
import Model.Disciplina;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DisciplinaView {

    private final DisciplinaController disciplinaController = new DisciplinaController();
    private final Scanner scanner = new Scanner(System.in);

    public void listarDisciplinas() {
        try {
            List<Disciplina> disciplinas = disciplinaController.listarDisciplinas();
            disciplinas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Erro ao listar disciplinas: " + e.getMessage());
        }
    }

    public void buscarDisciplinaPorId() {
        System.out.print("Digite o ID da disciplina: ");
        String id = scanner.nextLine();
        Optional<Disciplina> disciplina = disciplinaController.buscarDisciplinaPorId(id);
        disciplina.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Disciplina não encontrada.")
        );
    }
}

