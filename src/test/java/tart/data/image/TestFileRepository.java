package tart.data.image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import tart.domain.file.*;

public class TestFileRepository implements FileRepository {

    @Override
    public List<DirectoryInfo> getDirectories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DirectoryInfo> getDirectories(DirectoryInfo d) {
        throw new UnsupportedOperationException("Not supported yet.");
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
