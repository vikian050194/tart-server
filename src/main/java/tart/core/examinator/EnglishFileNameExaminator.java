package tart.core.examinator;

import java.util.Optional;
import tart.core.examinator.EnglishFileNameExaminator.EnglishFileNameParser;
import tart.core.matcher.FileMatcher;

public class EnglishFileNameExaminator extends FileNameExaminator<EnglishFileNameParser> {

    public class EnglishFileNameParser extends FileNameParser {

        private EnglishFileNameParser(String string) {
            this.string = string;
        }

        @Override
        public Optional<Integer> getYear() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Optional<Integer> getMonth() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Optional<Integer> getDay() {
            throw new UnsupportedOperationException("Not supported yet.");
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
