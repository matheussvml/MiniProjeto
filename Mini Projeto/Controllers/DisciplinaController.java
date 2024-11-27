package Controllers;

import Model.Aluno;
import Model.Disciplina;
import View.DisciplinaView;
import java.util.List;
import java.util.Set;

public class DisciplinaController {
        private final Disciplina disciplinaModel;
        private final DisciplinaView view;

        public DisciplinaController(String DisciplinasURL) {
                this.disciplinaModel = new Disciplina();
                this.view = new DisciplinaView();
        }

        // Exibir todas as disciplinas
        public void exibirDisciplinas() {
                List<Disciplina> disciplinas = disciplinaModel.getDisciplinas();
                view.listarDisciplinas(disciplinas);
        }

        // Exibir disciplinas matriculadas para um aluno específico
        public void listarDisciplinasMatriculadas(String alunoId) {
                Set<String> disciplinaIds = disciplinaModel.listarDisciplinasAluno(alunoId);
                List<Disciplina> disciplinas = disciplinaIds.stream()
                        .map(disciplinaModel::buscarDisciplina)
                        .filter(disciplina -> disciplina != null)
                        .toList();
                view.listarDisciplinasMatriculadas(disciplinas, alunoId);
        }

        // Matricular aluno em uma disciplina, verificando se o aluno está ativo
        public void matricularDisciplina(String alunoId, String disciplinaId) {
                Aluno aluno = new Aluno().buscarAluno(alunoId, null);  // Busca o aluno
                if (aluno != null && "ativo".equalsIgnoreCase(aluno.getStatus())) {
                        Disciplina disciplina = disciplinaModel.buscarDisciplina(disciplinaId);
                        if (disciplina != null) {
                                disciplinaModel.matricularAluno(alunoId, disciplinaId);
                                view.matriculaSucesso(alunoId, disciplina.getNome());
                        } else {
                                view.matriculaFalha(alunoId);
                        }
                } else {
                        System.out.println("Aluno inativo ou não encontrado. Matrícula não permitida.");
                }
        }

        // Remover disciplina de um aluno
        public void removerDisciplina(String alunoId, String disciplinaId) {
                disciplinaModel.removerDisciplina(alunoId, disciplinaId);
                view.removerDisciplinaSucesso(alunoId, disciplinaId);
        }
}
