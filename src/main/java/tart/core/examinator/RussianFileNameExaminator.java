package tart.core.examinator;

import java.util.Optional;
import tart.core.examinator.RussianFileNameExaminator.RussianFileNameParser;
import tart.core.matcher.FileMatcher;

public class RussianFileNameExaminator extends FileNameExaminator<RussianFileNameParser> {

    public class RussianFileNameParser extends FileNameParser {

        private RussianFileNameParser(String string) {
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
