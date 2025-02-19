package tart.core.matcher;

public class InvertedFileMatcher extends FileMatcher {

    private final FileMatcher wrappedMatcher;

    public InvertedFileMatcher(FileMatcher matcher) {
        super(".*");
        this.wrappedMatcher = matcher;
    }

    @Override
    public boolean isMatch(String string) {
        return !wrappedMatcher.isMatch(string);
    }
}
