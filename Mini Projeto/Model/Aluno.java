package Model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Aluno{
    private String id;
    private String nome;
    private String curso;
    private String modalidade;
    private String status;

    private static final String BASE_URL = "https://rmi6vdpsq8.execute-api.us-east-2.amazonaws.com/msAluno";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    // Armazena os dados carregados
    private static List<Aluno> alunoDados = null;

    public Aluno(String id, String nome, String curso, String modalidade, String status, List<Aluno> alunoDados) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.modalidade = modalidade;
        this.status = status;
        this.alunoDados = alunoDados;
        carregarDados();
    }

    public Aluno() {}

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Carrega os dados de alunos (somente uma vez)
    private static void carregarDados() {
        if (alunoDados == null) { // Verifica se os dados já foram carregados
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .GET()
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    alunoDados = mapper.readValue(response.body(), new TypeReference<List<Aluno>>() {});
                } else {
                    System.out.println("Erro ao buscar alunos: " + response.statusCode());
                    alunoDados = new ArrayList<>(); // Lista vazia em caso de erro
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                alunoDados = new ArrayList<>(); // Lista vazia em caso de erro
            }
        }
    }

    // Obter todos os alunos
    public List<Aluno> getAlunos() {
        carregarDados(); // Garante que os dados estão carregados
        return alunoDados;
    }

    // Filtrar alunos do curso de História e modalidade presencial
    public List<Aluno> filtrarAlunosHistoriaPresencial() {
        return getAlunos().stream()
                .filter(aluno -> "História".equals(aluno.getCurso()) && "Presencial".equals(aluno.getModalidade()))
                .collect(Collectors.toList());
    }

    // Buscar aluno específico por ID ou nome
    public Aluno buscarAluno(String id, String nome) {
        return getAlunos().stream()
                .filter(aluno -> (id != null && aluno.getId().equals(id)) ||
                        (nome != null && aluno.getNome().equalsIgnoreCase(nome)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                ", modalidade='" + modalidade + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
