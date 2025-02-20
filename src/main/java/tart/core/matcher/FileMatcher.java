package tart.core.matcher;

import java.util.regex.Pattern;

public abstract class FileMatcher {

    private final Pattern pattern;
    protected final FileMatcher matcher;

    public FileMatcher(String pattern) {
        this(pattern, null);
    }

    public FileMatcher(String pattern, FileMatcher matcher) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        this.matcher = matcher;
    }

    public boolean isMatch(String string) {
        var isCurrentMatch = pattern.matcher(string).matches();
        var isExternalMatch = matcher != null ? matcher.isMatch(string) : true;
        return isCurrentMatch && isExternalMatch;
    }
}
