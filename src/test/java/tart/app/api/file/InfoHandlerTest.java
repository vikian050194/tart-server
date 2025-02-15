package tart.app.api.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.app.api.BaseApiTest;
import tart.domain.file.TestFileRepository;

class InfoHandlerTest extends BaseApiTest {

    TestFileRepository getFileRepository() {
        return (TestFileRepository) dependencyFactory.getFileRepository();
    }

    @Test
    void testEmptyLists() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = new InfoResponse();
        var client = HttpClient.newHttpClient();
        var uri = new URI("%s/%s".formatted(baseAddress, "info"));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, InfoResponse.class);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void testOnlyDirs() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        getFileRepository().setDirectories(List.of("foo", "bar", "baz"));
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of("foo", "bar", "baz"));
        var client = HttpClient.newHttpClient();
        var uri = new URI("%s/%s".formatted(baseAddress, "info"));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, InfoResponse.class);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void testOnlyFiles() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        getFileRepository().setFiles(List.of("foo.png"));
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of(), List.of("foo.png"));
        var client = HttpClient.newHttpClient();
        var uri = new URI("%s/%s".formatted(baseAddress, "info"));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, InfoResponse.class);
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void testCustomDir() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        getFileRepository().setDirectories(List.of("root"));
        getFileRepository().setFiles(List.of("root.png"));
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of("root"), List.of("root.png"));
        var client = HttpClient.newHttpClient();
        var testDirName = "root";
        var uri = new URI("%s/%s/%s".formatted(baseAddress, "info", testDirName));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, InfoResponse.class);
        assertEquals(expectedBody, actualBody);
    }

}
