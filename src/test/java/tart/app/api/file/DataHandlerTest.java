package tart.app.api.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import tart.app.api.BaseApiTest;

class DataHandlerTest extends BaseApiTest {

    @Test
    void testEmptyImage() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = new byte[0];
        var client = HttpClient.newHttpClient();
        var testFileName = "foo/bar/baz.png";
        var uri = new URI("%s/%s/%s".formatted(baseAddress, "data", testFileName));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofByteArray());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var actualBody = response.body();
        assertArrayEquals(expectedBody, actualBody);
    }

}
