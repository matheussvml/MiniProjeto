package View;

import Model.Disciplina;
import java.util.List;

public class DisciplinaView {

    public void listarDisciplinas(List<Disciplina> disciplinas) {
        if (!disciplinas.isEmpty()) {
            System.out.println("Disciplinas disponíveis:");
            for (Disciplina disciplina : disciplinas) {
                System.out.println("ID: " + disciplina.getId() + ", Nome: " + disciplina.getNome());
            }
        } else {
            System.out.println("Nenhuma disciplina disponível.");
        }
    }

    public void listarDisciplinasMatriculadas(List<Disciplina> disciplinas, String nomeAluno) {
        if (!disciplinas.isEmpty()) {
            System.out.println("Disciplinas matriculadas por " + nomeAluno + ":");
            for (Disciplina disciplina : disciplinas) {
                System.out.println("ID: " + disciplina.getId() + ", Nome: " + disciplina.getNome());
            }
        } else {
            System.out.println("Nenhuma disciplina matriculada.");
        }
    }

    public void matriculaSucesso(String alunoId, String disciplinaNome) {
        System.out.println("Aluno " + alunoId + " matriculado com sucesso na disciplina: " + disciplinaNome);
    }

    // Exibir falha de matrícula devido à disciplina não ser do curso de História
    public void matriculaFalha(String alunoId) {
        System.out.println("Falha na matrícula. Verifique se a disciplina pertence ao curso de História.");
    }


    public void removerDisciplinaSucesso(String alunoId, String disciplinaId) {
        System.out.println("Disciplina " + disciplinaId + " removida com sucesso do aluno " + alunoId);
    }
}
