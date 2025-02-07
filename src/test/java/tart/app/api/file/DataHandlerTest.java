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
import tart.domain.file.DirectoryInfo;
import tart.domain.file.TestFileRepository;

class DataHandlerTest extends BaseApiTest {

    TestFileRepository getFileRepository() {
        return (TestFileRepository) dependencyFactory.getFileRepository();
    }

    @Test
    void testEmptyLists() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        getFileRepository().setDirectories(List.of(new DirectoryInfo(List.of("foo", "bar", "baz"))));
        var expectedStatus = 200;
        var expectedBody = new byte[0];
        var client = HttpClient.newHttpClient();
        var testFileName = "foo/bar/baz.png";
        var uri = new URI("%s/%s/%s".formatted(baseAddress, "data", testFileName));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, byte[].class);
        assertEquals(expectedBody, actualBody);
    }

}
