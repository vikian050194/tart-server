package tart.core.matcher.data;

import tart.core.matcher.FileMatcher;

public class ImgFileMatcher extends FileMatcher {

    public ImgFileMatcher() {
        this(null);
    }

    public ImgFileMatcher(FileMatcher matcher) {
        super("img_\\d{4}..*", matcher);
    }
}
