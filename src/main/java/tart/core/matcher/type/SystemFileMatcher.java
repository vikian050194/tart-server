package tart.core.matcher.type;

import tart.core.matcher.FileMatcher;

public class SystemFileMatcher extends FileMatcher {
    
    public SystemFileMatcher() {
        this(null);
    }
    
    public SystemFileMatcher(FileMatcher matcher) {
        super("\\..*", matcher);
    }
}
