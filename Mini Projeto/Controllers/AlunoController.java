package Controllers;
import Model.Aluno;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class AlunoController {

    private static final String BASE_URL = "https://rmi6vdpsq8.execute-api.us-east-2.amazonaws.com/msAluno";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public AlunoController() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public List<Aluno> listarAlunosHistoriaPresencial() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            List<Aluno> alunos = mapper.readValue(response.body(), new TypeReference<List<Aluno>>() {});
            return alunos.stream()
                    .filter(Aluno::isAlunoDeHistoriaPresencial)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Erro ao acessar o serviço de alunos: " + response.statusCode());
        }
    }

    public Optional<Aluno> buscarAlunoPorId(String id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return Optional.of(mapper.readValue(response.body(), Aluno.class));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }
        return Optional.empty();
    }
}

