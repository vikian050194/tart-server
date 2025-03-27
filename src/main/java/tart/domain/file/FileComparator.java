package tart.domain.file;

import java.util.Comparator;
import java.util.List;
import tart.core.examinator.EnglishFileNameExaminator;
import tart.core.examinator.FileNameExaminator;
import tart.core.examinator.FileNameParser;
import tart.core.examinator.RussianFileNameExaminator;

public class FileComparator implements Comparator<String> {

    // TODO extract getFileNameParser and reuse here and in FileService
    private FileNameParser getFileNameParser(String file) {
        var englishExaminator = new EnglishFileNameExaminator();
        var russianExaminator = new RussianFileNameExaminator();
        var examinators = List.of(englishExaminator, russianExaminator);
        // TODO is generic examinator necessary class structure?
        for (FileNameExaminator<? extends FileNameParser> examinator : examinators) {
            if (examinator.isMatch(file)) {
                return examinator.getNameParser(file);
            }
        }
        // TODO Optional or null?
        return null;
    }

    @Override
    public int compare(String f1, String f2) {
        var p1 = getFileNameParser(f1);
        if (p1 == null) {
            return 0;
        }
        var p2 = getFileNameParser(f2);
        if (p2 == null) {
            return 0;
        }
        var yearDelta = p1.getYear() - p2.getYear();
        if (yearDelta != 0) {
            return yearDelta;
        }
        var monthDelta = p1.getMonth() - p2.getMonth();
        if (monthDelta != 0) {
            return monthDelta;
        }
        var dayDelta = p1.getDay() - p2.getDay();
        if (dayDelta != 0) {
            return dayDelta;
        }
        var hourDelta = p1.getHour() - p2.getHour();
        if (hourDelta != 0) {
            return hourDelta;
        }
        var minuteDelta = p1.getMinute() - p2.getMinute();
        if (minuteDelta != 0) {
            return minuteDelta;
        }
        var secondDelta = p1.getSecond() - p2.getSecond();
        if (secondDelta != 0) {
            return secondDelta;
        }
        return 0;
    }

}
