package Service;

import Model.Disciplina;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class DisciplinaService {

    private static final String BASE_URL = "https://sswfuybfs8.execute-api.us-east-2.amazonaws.com/disciplinaServico/msDisciplina";
    private final HttpClient client;
    private final ObjectMapper mapper;

    // Construtor que inicializa o cliente HTTP e o mapeador de JSON
    public DisciplinaService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    // Método para listar todas as disciplinas do microsserviço
    public List<Disciplina> listarDisciplinas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Converte o JSON em uma lista de objetos Disciplina
            return mapper.readValue(response.body(), new TypeReference<List<Disciplina>>() {});
        } else {
            throw new RuntimeException("Erro ao acessar o serviço de disciplinas: " + response.statusCode());
        }
    }

    // Método para buscar uma disciplina específica pelo ID
    public Disciplina buscarDisciplinaPorId(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Converte o JSON em um objeto Disciplina
            return mapper.readValue(response.body(), Disciplina.class);
        } else {
            throw new RuntimeException("Erro ao buscar disciplina por ID: " + response.statusCode());
        }
    }
}
