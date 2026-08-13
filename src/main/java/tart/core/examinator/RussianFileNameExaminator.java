package tart.core.examinator;

import java.util.List;
import tart.core.examinator.RussianFileNameExaminator.RussianFileNameParser;
import tart.core.matcher.FileMatcher;

public class RussianFileNameExaminator extends FileNameExaminator<RussianFileNameParser> {

    public class RussianFileNameParser extends FileNameParser {

        private static final int DATE_INDEX = 0;
        private static final int TIME_INDEX = 1;

        private static final int START_INDEX = 0;

        private static final int YEAR_SIZE = 4;
        private static final int MONTH_SIZE = 2;
        private static final int DAY_SIZE = 2;

        private static final int HOUR_SIZE = 2;
        private static final int MINUTE_SIZE = 2;
        private static final int SECOND_SIZE = 2;

        private RussianFileNameParser(String string) {
            this.string = string;
            chunks = List.of(string.split("[_\\.]"));
        }

        @Override
        public String getExtension() {
            return chunks.getLast();
        }

        @Override
        public int getYear() {
            return Integer.parseInt(chunks.get(DATE_INDEX).substring(START_INDEX, YEAR_SIZE));
        }

        @Override
        public int getMonth() {
            var beginIndex = START_INDEX + YEAR_SIZE;
            var endIndex = beginIndex + MONTH_SIZE;
            return Integer.parseInt(chunks.get(DATE_INDEX).substring(beginIndex, endIndex));
        }

        @Override
        public int getDay() {
            var beginIndex = START_INDEX + YEAR_SIZE + MONTH_SIZE;
            var endIndex = beginIndex + DAY_SIZE;
            return Integer.parseInt(chunks.get(DATE_INDEX).substring(beginIndex, endIndex));
        }

        @Override
        public int getHour() {
            return Integer.parseInt(chunks.get(TIME_INDEX).substring(START_INDEX, HOUR_SIZE));
        }

        @Override
        public int getMinute() {
            var beginIndex = START_INDEX + HOUR_SIZE;
            var endIndex = beginIndex + MINUTE_SIZE;
            return Integer.parseInt(chunks.get(TIME_INDEX).substring(beginIndex, endIndex));
        }

        @Override
        public int getSecond() {
            var beginIndex = START_INDEX + HOUR_SIZE + MINUTE_SIZE;
            var endIndex = beginIndex + SECOND_SIZE;
            return Integer.parseInt(chunks.get(TIME_INDEX).substring(beginIndex, endIndex));
        }

    }

    public RussianFileNameExaminator() {
        this(null);
    }

    public RussianFileNameExaminator(FileMatcher matcher) {
        super("\\d{8}_\\d{6}.*", matcher);
    }

    @Override
    public RussianFileNameParser getNameParser(String string) {
        return new RussianFileNameParser(string);
    }
}
