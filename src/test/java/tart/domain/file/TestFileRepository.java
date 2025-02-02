package tart.domain.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TestFileRepository implements FileRepository {

    @Override
    public List<DirectoryInfo> getDirectories() {
        return List.of(new DirectoryInfo(List.of("foo", "bar", "baz")));
    }

    @Override
    public List<DirectoryInfo> getDirectories(DirectoryInfo d) {
        return List.of(new DirectoryInfo(List.of("root", "foo", "bar", "baz")));
    }

    @Override
    public List<FileInfo> getDescriptions(DirectoryInfo d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FileData getData(FileInfo f) throws IOException, FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(FileInfo f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(FileInfo f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
