import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP e buscar os top 250 filmes da API do IMBD

        // String imdbKey = System.getenv("IMDB_API_KEY");

        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";

        // String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";

        // String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";

        // String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";

        URI endereco = URI.create(url);
        /* HttpClient -> */ var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir e manipular os dados
        // for (Map<String, String> filme : listaDeFilmes) {
        // System.out.println(filme.get("title"));
        // System.out.println(filme.get("image"));
        // System.out.println(filme.get("imDbRating"));
        // System.out.println();
        // }

        // Outra forma de fazer
        for (int i = 0; i < listaDeFilmes.size(); i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            System.out.println("\u001b[1m\u001b[44;1mTítulo:\u001b[m " + filme.get("title"));
            System.out.println("\u001b[1mURL:\u001b[m " + filme.get("image"));
            System.out.print(filme.get("imDbRating"));
            System.out.print(" ");

            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int) classificacao;
            for (int n = 1; n <= numeroEstrelinhas; n++) {
                System.out.print("⭐");
            }

            System.out.println("\n");

        }

    }
}
