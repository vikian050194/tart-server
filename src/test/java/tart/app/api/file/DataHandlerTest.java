package tart.app.api.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.app.api.BaseApiTest;
import tart.app.api.Constants;

class DataHandlerTest extends BaseApiTest {

    @Test
    void testJpegImage() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = new byte[0];
        var client = HttpClient.newHttpClient();
        var testFileName = "im.jpeg";
        var uri = new URI("%s/%s/%s".formatted(baseAddress, "data", testFileName));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofByteArray());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var actualBody = response.body();
        assertArrayEquals(expectedBody, actualBody);
        assertEquals(Constants.IMAGE_JPEG, response.headers().firstValue(Constants.CONTENT_TYPE).get());
    }

    @Test
    void testJpgImage() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = new byte[0];
        var client = HttpClient.newHttpClient();
        var testFileName = "im.jpg";
        var uri = new URI("%s/%s/%s".formatted(baseAddress, "data", testFileName));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofByteArray());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var actualBody = response.body();
        assertArrayEquals(expectedBody, actualBody);
        assertEquals(Constants.IMAGE_JPEG, response.headers().firstValue(Constants.CONTENT_TYPE).get());
    }

    @Test
    void testPngImage() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = new byte[0];
        var client = HttpClient.newHttpClient();
        var testFileName = "im.png";
        var uri = new URI("%s/%s/%s".formatted(baseAddress, "data", testFileName));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofByteArray());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var actualBody = response.body();
        assertArrayEquals(expectedBody, actualBody);
        assertEquals(Constants.IMAGE_PNG, response.headers().firstValue(Constants.CONTENT_TYPE).get());
    }

    @Test
    void testMp4Video() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = new byte[0];
        var client = HttpClient.newHttpClient();
        var testFileName = "v.mp4";
        var uri = new URI("%s/%s/%s".formatted(baseAddress, "data", testFileName));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofByteArray());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var actualBody = response.body();
        assertArrayEquals(expectedBody, actualBody);
        assertEquals(Constants.VIDEO_MP4, response.headers().firstValue(Constants.CONTENT_TYPE).get());
    }
}
