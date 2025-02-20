package tart.core.examinator;

import java.util.Optional;
import tart.core.examinator.EnglishFileExaminator.EnglishFileNameParser;
import tart.core.matcher.FileMatcher;

public class EnglishFileExaminator extends FileNameExaminator<EnglishFileNameParser> {

    public class EnglishFileNameParser extends FileNameParser {

        private EnglishFileNameParser(String string) {
            this.string = string;
        }

        @Override
        public Optional<Integer> getYear() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public EnglishFileExaminator() {
        this(null);
    }

    public EnglishFileExaminator(FileMatcher matcher) {
        super("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}.*", matcher);
    }

    @Override
    public EnglishFileNameParser getNameParser(String string) {
        return new EnglishFileNameParser(string);
    }
}
