package tart.data.image;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import tart.data.file.LocalFileRepository;
import tart.domain.file.FileRepository;

public class LocalFileRepositoryTests {

    @Test
    public void getDirectoriesAtTarget() {
        // Arrange
        var target = List.of("home", "kirill", "git");
        FileRepository fr = new LocalFileRepository();
        var expectedSize = 32;

        // Act
        var actualSize = fr.getDirectories(target).size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getFiles() {
        // Arrange
        var target = List.of("home", "kirill", "git", "tart", "server");
        FileRepository fr = new LocalFileRepository();
        var expectedSize = 7;

        // Act
        var actualSize = fr.getFiles(target).size();

        // Assert
        assertEquals(expectedSize, actualSize);
    }
}
