package tart.core.examinator;

import java.util.List;

public abstract class FileNameParser {

    protected String string;
    protected List<String> chunks;

    public abstract int getYear();

    public abstract int getMonth();

    public abstract int getDay();

    public abstract int getHour();

    public abstract int getMinute();

    public abstract int getSecond();
}
