package tart.core.examinator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EnglishFileNameExaminatorTests {

    @Test
    public void isMatchFail() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "20210502_110954.png";
        var expected = false;

        // Act
        var actual = e.isMatch(testString);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void isMatchSuccess() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = true;

        // Act
        var actual = e.isMatch(testString);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void parserGetYear() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = 2020;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getYear();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void parserGetMonth() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = 4;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getMonth();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void parserGetDay() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = 9;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getDay();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void parserGetHour() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = 21;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getHour();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void parserGetMinute() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = 11;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getMinute();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void parserGetSecond() {
        // Arrange
        var e = new EnglishFileNameExaminator();
        var testString = "2020-04-09 21-11-40.JPG";
        var expected = 40;

        // Act
        var parser = e.getNameParser(testString);
        var actual = parser.getSecond();

        // Assert
        assertEquals(expected, actual);
    }
}
