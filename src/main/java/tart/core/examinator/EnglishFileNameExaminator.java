package tart.core.examinator;

import java.util.List;
import tart.core.examinator.EnglishFileNameExaminator.EnglishFileNameParser;
import tart.core.matcher.FileMatcher;

public class EnglishFileNameExaminator extends FileNameExaminator<EnglishFileNameParser> {

    public class EnglishFileNameParser extends FileNameParser {

        private static final int YEAR_INDEX = 0;
        private static final int MONTH_INDEX = 1;
        private static final int DAY_INDEX = 2;

        private static final int HOUR_INDEX = 3;
        private static final int MINUTE_INDEX = 4;
        private static final int SECOND_INDEX = 5;

        private EnglishFileNameParser(String string) {
            this.string = string;
            chunks = List.of(string.split("[_ -\\.]"));
        }

        @Override
        public String getExtension() {
            return chunks.getLast();
        }
        
        @Override
        public int getYear() {
            return Integer.parseInt(chunks.get(YEAR_INDEX));
        }

        @Override
        public int getMonth() {
            return Integer.parseInt(chunks.get(MONTH_INDEX));
        }

        @Override
        public int getDay() {
            return Integer.parseInt(chunks.get(DAY_INDEX));
        }

        @Override
        public int getHour() {
            return Integer.parseInt(chunks.get(HOUR_INDEX));
        }

        @Override
        public int getMinute() {
            return Integer.parseInt(chunks.get(MINUTE_INDEX));
        }

        @Override
        public int getSecond() {
            return Integer.parseInt(chunks.get(SECOND_INDEX));
        }

    }

    public EnglishFileNameExaminator() {
        this(null);
    }

    public EnglishFileNameExaminator(FileMatcher matcher) {
        super("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}.*", matcher);
    }

    @Override
    public EnglishFileNameParser getNameParser(String string) {
        return new EnglishFileNameParser(string);
    }
}
