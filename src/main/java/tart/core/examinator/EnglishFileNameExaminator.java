package tart.core.examinator;

import java.util.List;
import tart.core.examinator.EnglishFileNameExaminator.EnglishFileNameParser;
import tart.core.matcher.FileMatcher;

public class EnglishFileNameExaminator extends FileNameExaminator<EnglishFileNameParser> {

    public class EnglishFileNameParser extends FileNameParser {

        private EnglishFileNameParser(String string) {
            this.string = string;
            chunks = List.of(string.split("[ -\\.]"));
        }

        @Override
        public int getYear() {
            return Integer.parseInt(chunks.get(0));
        }

        @Override
        public int getMonth() {
            return Integer.parseInt(chunks.get(1));
        }

        @Override
        public int getDay() {
            return Integer.parseInt(chunks.get(2));
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
