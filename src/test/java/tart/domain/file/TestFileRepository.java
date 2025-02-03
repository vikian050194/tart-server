package tart.domain.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TestFileRepository implements FileRepository {

    private List<DirectoryInfo> dirs = List.of();
    private List<FileInfo> files = List.of();

    @Override
    public List<DirectoryInfo> getDirectories() {
        return dirs;
    }

    public void setDirectories(List<DirectoryInfo> v) {
        dirs = v;
    }

    @Override
    public List<DirectoryInfo> getDirectories(DirectoryInfo d) {
        return dirs;
    }

    public void setFiles(List<FileInfo> v) {
        files = v;
    }

    @Override
    public List<FileInfo> getFiles() {
        return files;
    }

    @Override
    public List<FileInfo> getFiles(DirectoryInfo d) {
        return files;
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
