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

class InfoHandlerTest extends BaseApiTest {

    @Test
    void testNoDirParamIsProvided() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
//        var imageService = (TestFileService) dependencyFactory.getFileService();
//        imageService.setDirectories(List.of(new DirectoryInfo(List.of("all")))));
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of(List.of("foo", "bar", "baz")));
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
    void testSingleDirParamIsProvided() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of(List.of("root", "foo", "bar", "baz")));
        var client = HttpClient.newHttpClient();
        var uri = new URI("%s/%s?dir=root".formatted(baseAddress, "info"));

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
