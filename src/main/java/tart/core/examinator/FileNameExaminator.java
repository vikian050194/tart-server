package tart.core.examinator;

import tart.core.matcher.FileMatcher;

// TODO Is it needed to extend FileMatcher that has isMatch?
public abstract class FileNameExaminator<T extends FileNameParser> extends FileMatcher {

    public FileNameExaminator(String pattern) {
        super(pattern);
    }

    public FileNameExaminator(String pattern, FileMatcher matcher) {
        super(pattern, matcher);
    }

    public abstract T getNameParser(String string);
}
