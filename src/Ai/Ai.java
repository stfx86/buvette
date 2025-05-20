import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Ai {

    // Your API key - replace with your actual key
    private static final String API_KEY = "AIzaSyB3VFJjJMj6i0JG-HCjaxvbet1P52gD7to";
    private static final String MODEL = "gemini-2.0-flash";
    private static final String API_URL = 
        "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL + ":generateContent?key=" + API_KEY;

    public static void main(String[] args) {
        try {
            // Create HTTP client
            HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

            // Prepare JSON request body
            String requestBody = "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"explain java swing \"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

            // Build request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Display the response
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            
            // Note: In a production application, you would parse the JSON response
            // using a library like Jackson or Gson

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}