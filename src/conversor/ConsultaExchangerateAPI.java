package conversor;

import com.google.gson.*;
import modelo.TabelaMoedas;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaExchangerateAPI {

       public TabelaMoedas consultar(String moeda){
        String enderecoApi = "https://v6.exchangerate-api.com/v6/67d2ca7301090e9fbe270db5/latest/" + moeda.toUpperCase();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CASE_WITH_UNDERSCORES)
                .create();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(enderecoApi))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject object = gson.fromJson(response.body(), JsonObject.class);

            JsonElement element = object.get("conversion_rates");

            TabelaMoedas tabelaMoedas = gson.fromJson(element.toString(), TabelaMoedas.class);

             return tabelaMoedas;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
