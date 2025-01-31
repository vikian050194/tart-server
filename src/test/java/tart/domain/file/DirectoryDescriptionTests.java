package tart.domain.file;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DirectoryDescriptionTests {

    @Test
    public void getName() {
        // Arrange
        var expectedName = "baz";
        var dirs = List.of("foo", "bar", "baz");
        NodeInfo d = new DirectoryInfo(dirs);

        // Act
        var actualName = d.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    public void getDirs() {
        // Arrange
        var expectedDirs = List.of("foo", "bar");
        var dirs = List.of("foo", "bar", "baz");
        NodeInfo d = new DirectoryInfo(dirs);

        // Act
        var actualDirs = d.getDirs();

        // Assert
        assertEquals(expectedDirs, actualDirs);
    }

    @Test
    public void getFullName() {
        // Arrange
        var expectedFullName = List.of("foo", "bar", "baz");
        var dirs = List.of("foo", "bar", "baz");
        NodeInfo d = new DirectoryInfo(dirs);

        // Act
        var actualFullName = d.getFullName();

        // Assert
        assertEquals(expectedFullName, actualFullName);
    }
}
