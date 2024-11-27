package Controllers;

import Model.Aluno;
import View.AlunoView;
import java.util.List;

public class AlunoController {
    private final Aluno alunoModel;
    private final AlunoView view;

    public AlunoController(String AlunosURL) {
        this.alunoModel = new Aluno();
        this.view = new AlunoView();
    }

    // Listar alunos do curso de História e modalidade presencial
    public void listarAlunosHistoria() {
        List<Aluno> alunosHistoria = alunoModel.filtrarAlunosHistoriaPresencial();
        view.listarAlunos(alunosHistoria);
    }

    // Buscar um aluno específico por ID ou nome e exibir apenas se estiver ativo
    public void buscarAluno(String id, String nome) {
        Aluno aluno = alunoModel.buscarAluno(id, nome);
        if (aluno != null && "ativo".equalsIgnoreCase(aluno.getStatus())) {
            view.alunoInfo(aluno);
        } else if (aluno == null) {
            System.out.println("Aluno não encontrado.");
        } else {
            System.out.println("Aluno inativo. Não é possível exibir informações.");
        }
    }
}
