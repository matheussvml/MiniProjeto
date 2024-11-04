package Controllers;

import Model.Disciplina;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class DisciplinaController {

        private static final String BASE_URL = "https://sswfuybfs8.execute-api.us-east-2.amazonaws.com/disciplinaServico/msDisciplina";
        private final HttpClient client;
        private final ObjectMapper mapper;

        public DisciplinaController() {
                this.client = HttpClient.newHttpClient();
                this.mapper = new ObjectMapper();
        }

        public List<Disciplina> listarDisciplinas() throws Exception {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                        return mapper.readValue(response.body(), new TypeReference<List<Disciplina>>() {});
                } else {
                        throw new RuntimeException("Erro ao acessar o serviço de disciplinas: " + response.statusCode());
                }
        }

        public Optional<Disciplina> buscarDisciplinaPorId(String id) {
                try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(BASE_URL + "/" + id))
                                .GET()
                                .build();

                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        if (response.statusCode() == 200) {
                                return Optional.of(mapper.readValue(response.body(), Disciplina.class));
                        }
                } catch (Exception e) {
                        System.out.println("Erro ao buscar disciplina: " + e.getMessage());
                }
                return Optional.empty();
        }
}


