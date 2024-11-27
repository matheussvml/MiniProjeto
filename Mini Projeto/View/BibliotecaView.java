package View;

import java.util.List;

public class BibliotecaView {

    public void listarLivrosReservados(List<String> livros, String alunoNome) {
        System.out.println("Livros reservados por " + alunoNome + ":");
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro reservado.");
        } else {
            for (String livroId : livros) {
                System.out.println("- Livro ID: " + livroId);
            }
        }
    }

    public void reservaSucesso(String livroId) {
        System.out.println("Livro " + livroId + " reservado com sucesso!");
    }

    public void reservaFalha(String livroId) {
        System.out.println("Não foi possível reservar o livro " + livroId + ".");
    }

    public void cancelamentoSucesso(String livroId) {
        System.out.println("Reserva do livro " + livroId + " cancelada com sucesso.");
    }

    public void cancelamentoFalha(String livroId) {
        System.out.println("Não foi possível cancelar a reserva do livro " + livroId + ".");
    }
}
