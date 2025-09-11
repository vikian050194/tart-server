package tart.app.api.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoResponse {

    public List<String> dirs;
    public List<String> files;
    public List<Integer> years;
    public List<Integer> months;
    public List<Integer> days;

    public InfoResponse() {
        this(List.of(), List.of());
    }

    public InfoResponse(List<String> d) {
        this(d, List.of());
    }

    public InfoResponse(List<String> d, List<String> f) {
        dirs = d;
        files = f;
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof InfoResponse)) {
            return false;
        }

        var c = (InfoResponse) o;

        return dirs.equals(c.dirs) && files.equals(c.files) && years.equals(c.years) && months.equals(c.months) && days.equals(c.days);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(dirs);
        hash = 71 * hash + Objects.hashCode(files);
        hash = 71 * hash + Objects.hashCode(years);
        hash = 71 * hash + Objects.hashCode(months);
        hash = 71 * hash + Objects.hashCode(days);
        return hash;
    }
}
