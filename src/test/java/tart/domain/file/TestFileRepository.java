package tart.domain.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TestFileRepository implements FileRepository {

    private List<String> dirs = List.of();
    private List<String> files = List.of();
    private byte[] data = new byte[0];

    public void setDirectories(List<String> value) {
        dirs = value;
    }

    @Override
    public List<String> getDirectories(List<String> path) {
        return dirs;
    }

    public void setFiles(List<String> value) {
        files = value;
    }

    @Override
    public List<String> getFiles(List<String> path) {
        return files;
    }

    public void setData(byte[] value) {
        data = value;
    }

    @Override
    public byte[] getData(List<String> path) throws IOException, FileNotFoundException {
        return data;
    }

    @Override
    public boolean move(List<String> source, List<String> target) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(List<String> path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean makeDirectory(List<String> path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean exists(List<String> path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getAbsoluteFiles(List<String> path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
