import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CryptoPriceService {

    private static final String API_URL =
            "https://api.coingecko.com/api/v3/simple/price";

    private static final String API_KEY =
            System.getenv("COINGECKO_API_KEY");

    private final HttpClient httpClient;

    public CryptoPriceService() {
        httpClient =
                HttpClient.newHttpClient();
    }


    // =========================================================
    // GET CURRENT PRICES
    // =========================================================

    public Map<String, Double> getCurrentPrices(
            String... assetIds) {

        Map<String, Double> prices =
                new HashMap<>();

        if (assetIds == null ||
                assetIds.length == 0) {

            return prices;
        }

        if (API_KEY == null ||
                API_KEY.isBlank()) {

            System.out.println(
                    "COINGECKO_API_KEY environment variable is not set."
            );

            return prices;
        }

        String ids =
                String.join(",", assetIds);

        String url =
                API_URL
                        + "?ids="
                        + ids
                        + "&vs_currencies=inr";

        try {

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(url)
                            )
                            .header(
                                    "x-cg-demo-api-key",
                                    API_KEY
                            )
                            .header(
                                    "Accept",
                                    "application/json"
                            )
                            .GET()
                            .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {

                System.out.println(
                        "CoinGecko API request failed. HTTP status: "
                                + response.statusCode()
                );

                return prices;
            }

            JsonObject root =
                    JsonParser.parseString(
                            response.body()
                    ).getAsJsonObject();

            for (String assetId :
                    assetIds) {

                if (!root.has(assetId)) {
                    continue;
                }

                JsonObject coin =
                        root.getAsJsonObject(
                                assetId
                        );

                if (coin.has("inr")) {

                    double price =
                            coin.get("inr")
                                    .getAsDouble();

                    prices.put(
                            assetId,
                            price
                    );
                }
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Crypto price request was interrupted."
            );

        } catch (Exception e) {

            System.out.println(
                    "Error fetching crypto prices: "
                            + e.getMessage()
            );
        }

        return prices;
    }


    // =========================================================
    // GET ONE CURRENT PRICE
    // =========================================================

    public Double getCurrentPrice(
            String assetId) {

        if (assetId == null ||
                assetId.trim().isEmpty()) {

            return null;
        }

        Map<String, Double> prices =
                getCurrentPrices(
                        assetId.trim().toLowerCase()
                );

        return prices.get(
                assetId.trim().toLowerCase()
        );
    }
}