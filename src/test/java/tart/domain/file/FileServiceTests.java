package tart.domain.file;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FileServiceTests {

    @Test
    public void showSystemDirs() {
        // Arrange
        var expectedValue = false;
        var s = new FileService(new TestFileRepository());

        // Act
        var actualValue = s.showSystemDirs();

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void showSystemFiles() {
        // Arrange
        var expectedValue = false;
        var s = new FileService(new TestFileRepository());

        // Act
        var actualValue = s.showSystemFiles();

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void testGetDirectoriesEmpty() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        var fileService = new FileService(testFileRepository);
        var expected = List.of();

        // Act
        var actual = fileService.getDirectories();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetDirectories() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        testFileRepository.setDirectories(List.of("a", "b", "c"));
        var fileService = new FileService(testFileRepository);
        var expected = List.of("a", "b", "c");

        // Act
        var actual = fileService.getDirectories();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetDirectoriesFiltersSystemDir() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        testFileRepository.setDirectories(List.of("regular", ".system"));
        var fileService = new FileService(testFileRepository);
        var expected = List.of("regular");

        // Act
        var actual = fileService.getDirectories();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetDirectoriesCustomDir() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        testFileRepository.setDirectories(List.of("sub"));
        var testDir = List.of("custom");
        var fileService = new FileService(testFileRepository);
        var expected = List.of("sub");

        // Act
        var actual = fileService.getDirectories(testDir);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetFiles() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        testFileRepository.setFiles(List.of("im1.jpeg", "im2.jpg", "im3.png", "v.mp4"));
        var fileService = new FileService(testFileRepository);
        var expected = List.of("im1.jpeg", "im2.jpg", "im3.png", "v.mp4");

        // Act
        var actual = fileService.getFiles();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetFilesFilterSystemFile() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        testFileRepository.setFiles(List.of("regular.jpeg", ".system"));
        var fileService = new FileService(testFileRepository);
        var expected = List.of("regular.jpeg");

        // Act
        var actual = fileService.getFiles();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetFilesFilterNonSupportedFile() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        testFileRepository.setFiles(List.of("regular.jpeg", "notes.txt"));
        var fileService = new FileService(testFileRepository);
        var expected = List.of("regular.jpeg");

        // Act
        var actual = fileService.getFiles();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testGetFilesCustomDir() throws IOException, InterruptedException {
        // Arrange
        var testFileRepository = new TestFileRepository();
        testFileRepository.setFiles(List.of("regular.jpeg"));
        var testDir = List.of("custom");
        var fileService = new FileService(testFileRepository);
        var expected = List.of("regular.jpeg");

        // Act
        var actual = fileService.getFiles(testDir);

        // Assert
        assertEquals(expected, actual);
    }
}
