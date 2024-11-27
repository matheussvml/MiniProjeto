package Model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Disciplina {
    private String id;
    private String curso;
    private String nome;

    private static final String BASE_URL = "https://sswfuybfs8.execute-api.us-east-2.amazonaws.com/disciplinaServico/msDisciplina";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private Map<String, Set<String>> matriculas = new HashMap<>();

    public Disciplina(String id, String curso, String nome) {
        this.id = id;
        this.curso = curso;
        this.nome = nome;
    }

    public Disciplina() {
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // Obter disciplinas do microsserviço
    public List<Disciplina> getDisciplinas() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Disciplina>>() {});
            } else {
                System.out.println("Erro ao buscar disciplinas: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return List.of();
    }

    // Buscar disciplina por ID
    public Disciplina buscarDisciplina(String disciplinaId) {
        List<Disciplina> disciplinas = getDisciplinas();
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId().equals(disciplinaId)) {
                return disciplina;
            }
        }
        return null;
    }

    // Matricular aluno em uma disciplina
    public void matricularAluno(String alunoId, String disciplinaId) {
        matriculas.computeIfAbsent(alunoId, k -> new HashSet<>()).add(disciplinaId);
    }

    // Listar disciplinas de um aluno
    public Set<String> listarDisciplinasAluno(String alunoId) {
        return matriculas.getOrDefault(alunoId, Set.of());
    }

    // Remover disciplina de um aluno
    public void removerDisciplina(String alunoId, String disciplinaId) {
        Set<String> disciplinasAluno = matriculas.get(alunoId);
        if (disciplinasAluno != null) {
            disciplinasAluno.remove(disciplinaId);
        }
    }

    @Override
    public String toString() {
        return "Disciplina [id=" + id + ", curso=" + curso + ", nome=" + nome + "]";
    }
}
