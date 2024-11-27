package View;

import Model.Aluno;
import java.util.List;

public class AlunoView {

    // Listar todos os alunos
    public void listarAlunos(List<Aluno> alunos) {
        System.out.println("\nAlunos cadastrados:");
        for (Aluno aluno : alunos) {
            System.out.println("ID: " + aluno.getId() + ", Nome: " + aluno.getNome());
        }
        System.out.println(alunos.size() + " aluno(s) listado(s)");
    }

    // Exibir detalhes de um aluno específico
    public void alunoInfo(Aluno aluno) {
        if (aluno != null) {
            System.out.println("Detalhes do Aluno: " + aluno);
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }
}
