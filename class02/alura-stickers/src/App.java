import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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

        // String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";

        // String url =
        // "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";

        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";

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
        // var geradora = new GeradoraDeFigurinhas();
        // for (Map<String, String> filme : listaDeFilmes) {

        // String urlImagem = filme.get("image");
        // String titulo = filme.get("title");

        // InputStream inputStream = new URL(urlImagem).openStream();
        // String nomeArquivo = titulo + ".png";

        // geradora.cria(inputStream, nomeArquivo);

        // System.out.println(titulo);
        // System.out.println();
        // }

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        // Outra forma de fazer
        for (int i = 0; i < listaDeFilmes.size(); i++) {

            Map<String, String> filme = listaDeFilmes.get(i);

            String urlImagem = filme.get("image");
            InputStream inputStream = new URL(urlImagem).openStream();

            String titulo = filme.get("title");
            String nomeArquivo = "figurinhas/" + titulo.replace(":", "-") + ".png";

            System.out.println("\u001b[1m\u001b[44;1mTítulo:\u001b[m " + filme.get("title"));
            System.out.print(filme.get("imDbRating"));
            System.out.print(" ");

            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int) classificacao;
            String textoFigurinha;
            InputStream imagemAvaliacao;
            if (numeroEstrelinhas >= 8) {
                textoFigurinha = "TOPZERA";
                imagemAvaliacao = new FileInputStream(new File("sobreposicao/bom.png"));
                for (int n = 1; n <= numeroEstrelinhas; n++) {
                    System.out.print("⭐");
                }
            } else {
                textoFigurinha = "HUMMMM...";
                imagemAvaliacao = new FileInputStream(new File("sobreposicao/ruim.png"));
                for (int n = 1; n <= numeroEstrelinhas; n++) {
                    System.out.print("🍅");
                }
            }

            var geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemAvaliacao);

            System.out.println("\n");

        }

    }
}
