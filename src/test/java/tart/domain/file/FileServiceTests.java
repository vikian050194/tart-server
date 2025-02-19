package tart.domain.file;

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
}
