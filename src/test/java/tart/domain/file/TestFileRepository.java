package tart.domain.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TestFileRepository implements FileRepository {

    private List<DirectoryInfo> dirs = List.of();
    private List<FileInfo> files = List.of();
    private FileData data = new FileData(new byte[0]);

    public void setDirectories(List<DirectoryInfo> value) {
        dirs = value;
    }

    @Override
    public List<DirectoryInfo> getDirectories(DirectoryInfo d) {
        return dirs;
    }

    public void setFiles(List<FileInfo> value) {
        files = value;
    }

    @Override
    public List<FileInfo> getFiles(DirectoryInfo d) {
        return files;
    }

    public void setData(FileData value) {
        data = value;
    }

    @Override
    public FileData getData(FileInfo f) throws IOException, FileNotFoundException {
        return data;
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
