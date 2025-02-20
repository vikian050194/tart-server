package tart.core.examinator;

import java.util.Optional;

public abstract class FileNameParser {

    protected String string;
    
    public abstract Optional<Integer> getYear();
}
