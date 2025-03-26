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
        getFileRepository().setDirectories(List.of("a", "b", "c"));
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of("a", "b", "c"));
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
        getFileRepository().setFiles(List.of("11112233_445566.jpeg", "11112233_445566.jpg", "11112233_445566.png"));
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of(), List.of("11112233_445566.jpeg", "11112233_445566.jpg", "11112233_445566.png"));
        expectedBody.years.add(1111);
        expectedBody.months.add(22);
        expectedBody.days.add(33);
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
        getFileRepository().setFiles(List.of("11112233_445566.png"));
        var expectedStatus = 200;
        var expectedBody = new InfoResponse(List.of("root"), List.of("11112233_445566.png"));
        expectedBody.years.add(1111);
        expectedBody.months.add(22);
        expectedBody.days.add(33);
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

    @Test
    void testAllDateData() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        // TODO png -> mp4
        getFileRepository().setFiles(List.of("2020-04-09 21-11-40.JPG", "20210502_110954.png"));
        var expectedStatus = 200;
        var expectedYears = List.of(2020, 2021);
        var expectedMonths = List.of(4, 5);
        var expectedDays = List.of(2, 9);
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
        assertEquals(expectedYears, actualBody.years);
        assertEquals(expectedMonths, actualBody.months);
        assertEquals(expectedDays, actualBody.days);
    }

    @Test
    void testYearsFilter() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        getFileRepository().setFiles(List.of("2020-04-09 21-11-40.JPG", "20210502_110954.png"));
        var expectedStatus = 200;
        var expectedYears = List.of(2020);
        var expectedMonths = List.of(4);
        var expectedDays = List.of(9);
        var client = HttpClient.newHttpClient();
        var testDirName = "root";
        var testYear = 2020;
        var uri = new URI("%s/%s/%s?years=%s".formatted(baseAddress, "info", testDirName, testYear));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, InfoResponse.class);
        assertEquals(expectedYears, actualBody.years);
        assertEquals(expectedMonths, actualBody.months);
        assertEquals(expectedDays, actualBody.days);
    }

    @Test
    void testMonthsFilter() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        getFileRepository().setFiles(List.of("2020-04-09 21-11-40.JPG", "20210502_110954.png"));
        var expectedStatus = 200;
        var expectedYears = List.of(2020);
        var expectedMonths = List.of(4);
        var expectedDays = List.of(9);
        var client = HttpClient.newHttpClient();
        var testDirName = "root";
        var testMonth = 4;
        var uri = new URI("%s/%s/%s?months=%s".formatted(baseAddress, "info", testDirName, testMonth));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, InfoResponse.class);
        assertEquals(expectedYears, actualBody.years);
        assertEquals(expectedMonths, actualBody.months);
        assertEquals(expectedDays, actualBody.days);
    }

    @Test
    void testDaysFilter() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        getFileRepository().setFiles(List.of("2020-04-09 21-11-40.JPG", "20210502_110954.png"));
        var expectedStatus = 200;
        var expectedYears = List.of(2020);
        var expectedMonths = List.of(4);
        var expectedDays = List.of(9);
        var client = HttpClient.newHttpClient();
        var testDirName = "root";
        var testDay = 9;
        var uri = new URI("%s/%s/%s?days=%s".formatted(baseAddress, "info", testDirName, testDay));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        var om = new ObjectMapper();
        String body = response.body();
        var actualBody = om.readValue(body, InfoResponse.class);
        assertEquals(expectedYears, actualBody.years);
        assertEquals(expectedMonths, actualBody.months);
        assertEquals(expectedDays, actualBody.days);
    }
}
