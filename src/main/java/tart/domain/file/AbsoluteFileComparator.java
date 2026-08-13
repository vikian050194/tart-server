package tart.domain.file;

import java.io.File;

public class AbsoluteFileComparator extends FileComparator {

    private String splitEnd(String source) {
        var chunks = source.split(File.separator);
        return chunks[chunks.length - 1];
    }
    
    @Override
    public int compare(String f1, String f2) {
        var fileName1 = splitEnd(f1);
        var fileName2 = splitEnd(f2);
        return super.compare(fileName1, fileName2);
    }

}
