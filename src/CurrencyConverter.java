import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/1692953f0457ca5f391e1377/latest/BRL";
    private static final Map<String, Double> exchangeRates = new HashMap<>();

    public static void main(String[] args) {
        fetchExchangeRates();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao conversor de moeda!");
        System.out.println("Moedas disponíveis: USD, EUR, GBP, JPY, AUD");

        while (true) {
            System.out.print("Digite a moeda de origem (ou 'sair' para finalizar): ");
            String fromCurrency = scanner.nextLine().toUpperCase();
            if (fromCurrency.equals("SAIR")) {
                break;
            }

            System.out.print("Digite a moeda de destino: ");
            String toCurrency = scanner.nextLine().toUpperCase();

            System.out.print("Digite o valor a ser convertido: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // Consome a nova linha

            double convertedAmount = convertCurrency(fromCurrency, toCurrency, amount);
            System.out.printf("Resultado: %.2f %s = %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
        }

        scanner.close();
    }

    private static void fetchExchangeRates() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String json = EntityUtils.toString(response.getEntity());
                Gson gson = new Gson();
                ExchangeRateResponse exchangeRateResponse = gson.fromJson(json, ExchangeRateResponse.class);

                exchangeRates.put("USD", exchangeRateResponse.conversionRates.get("USD"));
                exchangeRates.put("EUR", exchangeRateResponse.conversionRates.get("EUR"));
                exchangeRates.put("GBP", exchangeRateResponse.conversionRates.get("GBP"));
                exchangeRates.put("JPY", exchangeRateResponse.conversionRates.get("JPY"));
                exchangeRates.put("AUD", exchangeRateResponse.conversionRates.get("AUD"));
                exchangeRates.put("BRL", 1.0); // Taxa de conversão para BRL é 1:1
            }
        } catch (IOException e) {
            System.err.println("Erro ao buscar taxas de câmbio: " + e.getMessage());
        }
    }

    private static double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        if (!exchangeRates.containsKey(fromCurrency) || !exchangeRates.containsKey(toCurrency)) {
            System.err.println("Moeda não suportada.");
            return 0;
        }

        double fromRate = exchangeRates.get(fromCurrency);
        double toRate = exchangeRates.get(toCurrency);
        return (amount / fromRate) * toRate;
    }

    private static class ExchangeRateResponse {
        String base_code;
        Map<String, Double> conversionRates;
    }
}
