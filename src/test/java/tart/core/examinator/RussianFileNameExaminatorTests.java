package tart.core.examinator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RussianFileNameExaminatorTests {

    @Test
    public void isMatchFail() {
        // Arrange
        var e = new RussianFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = false;

        // Act
        var actual = e.isMatch(testString);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void isMatchSuccess() {
        // Arrange
        var e = new RussianFileNameExaminator();
        var testString = "20210502_110954.mp4";
        var expected = true;

        // Act
        var actual = e.isMatch(testString);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void parserGetYear() {
        // Arrange
        var e = new RussianFileNameExaminator();
        var testString = "20210502_110954.mp4";
        var expected = 2021;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getYear();

        // Assert
        assertEquals(expected, actual.get());
    }

    @Test
    public void parserGetMonth() {
        // Arrange
        var e = new RussianFileNameExaminator();
        var testString = "20210502_110954.mp4";
        var expected = 5;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getMonth();

        // Assert
        assertEquals(expected, actual.get());
    }

    @Test
    public void parserGetDay() {
        // Arrange
        var e = new RussianFileNameExaminator();
        var testString = "20210502_110954.mp4";
        var expected = 2;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getDay();

        // Assert
        assertEquals(expected, actual.get());
    }
}
