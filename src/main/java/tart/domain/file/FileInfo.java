package tart.domain.file;

import java.util.List;

// TODO is it better to make parent and child classes instead of interface NodeInfo?
public class FileInfo implements NodeInfo {

    private final List<String> path;
    private static final String EXTENSION_DELIMITER = ".";

    public FileInfo(List<String> p) {
        path = p;
    }

    @Override
    public String getName() {
        return path.get(path.size() - 1);
    }

    public String getExtension() {
        var name = getName();
        var index = name.indexOf(EXTENSION_DELIMITER);
        return name.substring(index + 1);
    }

    @Override
    public List<String> getDirs() {
        return path.subList(0, path.size() - 1);
    }

    @Override
    public List<String> getFullName() {
        return path;
    }
}
