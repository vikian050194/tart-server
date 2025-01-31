package tart.domain.file;

import java.util.List;

public class DirectoryInfo implements NodeInfo {

    private final List<String> dirs;

    public DirectoryInfo(List<String> d) {
        dirs = d;
    }

    @Override
    public String getName() {
        var index = dirs.size() - 1;
        return dirs.get(index);
    }

    @Override
    public List<String> getDirs() {
        var index = dirs.size() - 1;
        return dirs.subList(0, index);
    }

    @Override
    public List<String> getFullName() {
        return List.copyOf(dirs);
    }
}
