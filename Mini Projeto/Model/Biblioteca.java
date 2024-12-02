package Model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Biblioteca {
    private String id;
    private String titulo;
    private String autor;
    private int ano;
    private String status;

    private static final String BASE_URL = "https://qiiw8bgxka.execute-api.us-east-2.amazonaws.com/acervo/biblioteca";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static Map<String, List<String>> reservas = new HashMap<>();
    private static List<Biblioteca> livrosDados = null; // Armazena os dados carregados

    public Biblioteca(String id, String titulo, String autor, int ano, String status,List<Biblioteca> livrosDados,Map<String, List<String>> reservas) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.status = status;
        this.reservas = reservas;
        this.livrosDados = livrosDados;
        carregarDados();
    }

    public Biblioteca() {}

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Carrega os dados de livros apenas uma vez
    private static void carregarDados() {
        if (livrosDados == null) { // Verifica se os dados já foram carregados
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    livrosDados = mapper.readValue(response.body(), new TypeReference<List<Biblioteca>>() {});
                    // Ajustar status de livros com status null
                    for (Biblioteca livro : livrosDados) {
                        if (livro.getStatus() == null || "null".equalsIgnoreCase(livro.getStatus())) {
                            livro.setStatus("disponível");
                        }
                    }
                } else {
                    System.out.println("Erro ao buscar livros: " + response.statusCode());
                    livrosDados = new ArrayList<>(); // Lista vazia em caso de erro
                }
            } catch (Exception e) {
                System.out.println("Erro ao carregar livros: " + e.getMessage());
                livrosDados = new ArrayList<>(); // Lista vazia em caso de erro
            }
        }
    }



    // Obter todos os livros
    public List<Biblioteca> getLivros() {
        carregarDados(); // Garante que os dados estão carregados
        return livrosDados;
    }

    // Buscar livro por ID
    public Biblioteca buscarLivro(String livroId) {
        return getLivros().stream()
                .filter(livro -> livro.getId().equals(livroId))
                .findFirst()
                .orElse(null);
    }

    // Reservar livro
    public boolean reservarLivro(String alunoId, String livroId) {
        List<String> livrosReservados = reservas.computeIfAbsent(alunoId, k -> new ArrayList<>());
        Biblioteca livro = buscarLivro(livroId);

        if (livro != null && "disponível".equalsIgnoreCase(livro.getStatus())) {
            livrosReservados.add(livroId);
            livro.setStatus("reservado");
            return true;
        }
        return false;
    }

    // Cancelar reserva de livro
    public boolean cancelarReserva(String alunoId, String livroId) {
        List<String> livrosReservados = reservas.get(alunoId);
        Biblioteca livro = buscarLivro(livroId);
        if (livro != null && livrosReservados != null && livrosReservados.remove(livroId)) {
            livro.setStatus("disponível");
            return true;
        }
        return false;
    }

    //    matriculas{
//        (alunoId)"1":(Id)["1"],
//        (alunoId)"2":(Id)["5"]
//    }

    // Listar livros reservados pelo aluno
    public List<String> listarLivrosReservados(String alunoId) {
        return reservas.getOrDefault(alunoId, List.of());
    }
    // Retorna o valor associado ao alunoId, se não tiver ele retorna uma lista vazia


}
