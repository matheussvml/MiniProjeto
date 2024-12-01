package Model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Disciplina {
    private String id;
    private String curso;
    private String nome;

    private static final String BASE_URL = "https://sswfuybfs8.execute-api.us-east-2.amazonaws.com/disciplinaServico/msDisciplina";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static List<Disciplina> disciplinasDados = null; // Armazena os dados carregados
    private Map<String, Set<String>> matriculas = new HashMap<>();

    public Disciplina(String id, String curso, String nome, List<Disciplina> disciplinasDados, Map<String, Set<String>> matriculas) {
        this.id = id;
        this.curso = curso;
        this.nome = nome;
        this.disciplinasDados = disciplinasDados;
        this.matriculas = matriculas;
        carregarDados();
    }

    public Disciplina() {}

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // Carregar dados de disciplinas apenas uma vez
    private static void carregarDados() {
        if (disciplinasDados == null) { // Verifica se os dados já foram carregados
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    disciplinasDados = mapper.readValue(response.body(), new TypeReference<List<Disciplina>>() {});
                } else {
                    System.out.println("Erro ao buscar disciplinas: " + response.statusCode());
                    disciplinasDados = List.of(); // Lista vazia em caso de erro
                }
            } catch (Exception e) {
                System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
                disciplinasDados = List.of(); // Lista vazia em caso de erro
            }
        }
    }

    // Obter todas as disciplinas
    public List<Disciplina> getDisciplinas() {
        carregarDados(); // Garante que os dados estão carregados
        return disciplinasDados;
    }

    // Cria um fluxo de dados (stream) a partir do conjunto das disciplinas
    // Buscar disciplina por ID
    public Disciplina buscarDisciplina(String disciplinaId) {
        return getDisciplinas().stream()
                .filter(disciplina -> disciplina.getId().equals(disciplinaId))
                .findFirst()
                .orElse(null);
    }

    // Matricular aluno em uma disciplina
    public void matricularAluno(String alunoId, String disciplinaId) {
        matriculas.computeIfAbsent(alunoId, k -> new HashSet<>()).add(disciplinaId);
    }

//    matriculas{
//        (alunoId)"1":(disciplinaId)["1"]
//    }

    // Listar disciplinas de um aluno
    public Set<String> listarDisciplinasAluno(String alunoId) {
        return matriculas.getOrDefault(alunoId, Set.of());
    }



    //Tenta obter o valor associado ao alunoId no mapa.
    //Se o alunoId não existir no mapa, retorna um valor padrão especificado, neste caso Set.of().


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
