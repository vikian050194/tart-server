package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class RussianFileMatcher extends FileMatcher {

    public RussianFileMatcher() {
        this(null);
    }

    public RussianFileMatcher(FileMatcher matcher) {
        super("\\d{8}_\\d{6}.*", matcher);
    }
}
