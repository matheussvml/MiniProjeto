package Controllers;

import Model.Aluno;
import Model.Biblioteca;
import View.BibliotecaView;

import java.util.List;

public class BibliotecaController {
    private final Biblioteca bibliotecaModel;
    private final BibliotecaView view;

    public BibliotecaController(String BibliotecaURL) {
        this.bibliotecaModel = new Biblioteca();
        this.view = new BibliotecaView();
    }

    // Reservar livro
    public void reservarLivro(String alunoId, String livroId) {
        Aluno aluno = new Aluno().buscarAluno(alunoId, null); // Buscar aluno pelo ID
        if (aluno != null && "ativo".equalsIgnoreCase(aluno.getStatus())) {
            boolean sucesso = bibliotecaModel.reservarLivro(alunoId, livroId);
            if (sucesso) {
                view.reservaSucesso(livroId);
            } else {
                view.reservaFalha(livroId);
            }
        } else {
            System.out.println("Aluno não encontrado ou inativo. Não é possível reservar livros.");
        }
    }

    // Cancelar reserva de livro
    public void cancelarReserva(String alunoId, String livroId) {
        Aluno aluno = new Aluno().buscarAluno(alunoId, null); // Buscar aluno pelo ID
        if (aluno != null && "ativo".equalsIgnoreCase(aluno.getStatus())) {
            boolean sucesso = bibliotecaModel.cancelarReserva(alunoId, livroId);
            if (sucesso) {
                view.cancelamentoSucesso(livroId);
            } else {
                view.cancelamentoFalha(livroId);
            }
        } else {
            System.out.println("Aluno não encontrado ou inativo. Não é possível cancelar reservas.");
        }
    }



    // Listar livros reservados por aluno
    public void listarLivrosReservados(String alunoId) {
        Aluno aluno = new Aluno().buscarAluno(alunoId, null); // Buscar aluno pelo ID
        if (aluno != null && "ativo".equalsIgnoreCase(aluno.getStatus())) {
            List<String> livrosReservados = bibliotecaModel.listarLivrosReservados(alunoId);
            view.listarLivrosReservados(livrosReservados, aluno.getNome());
        } else {
            System.out.println("Aluno não encontrado ou inativo. Não é possível listar reservas.");
        }
    }
}
