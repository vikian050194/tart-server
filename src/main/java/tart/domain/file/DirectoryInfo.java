package tart.domain.file;

import java.util.List;

public class DirectoryInfo implements NodeInfo {

    private final List<String> path;

    public DirectoryInfo(List<String> p) {
        path = p;
    }

    @Override
    public String getName() {
        var index = path.size() - 1;
        return path.get(index);
    }

    @Override
    public List<String> getDirs() {
        var index = path.size() - 1;
        return path.subList(0, index);
    }

    @Override
    public List<String> getFullName() {
        return List.copyOf(path);
    }
}
