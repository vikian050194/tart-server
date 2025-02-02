package tart.app.api.file;

import java.util.List;
import java.util.Objects;

public class InfoResponse {

    public List<List<String>> dirs;
    public List<List<String>> files;

    public InfoResponse() {

    }

    public InfoResponse(List<List<String>> d) {
        this(d, List.of());
    }

    public InfoResponse(List<List<String>> d, List<List<String>> f) {
        dirs = d;
        files = f;
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

        return dirs.equals(c.dirs) && files.equals(c.files);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(dirs);
        hash = 71 * hash + Objects.hashCode(files);
        return hash;
    }
}
