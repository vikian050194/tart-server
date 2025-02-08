package tart.domain.file;

import java.io.*;
import java.util.List;

public interface FileRepository {

    public List<String> getDirectories(List<String> path);

    public List<String> getFiles(List<String> path);

    public byte[] getData(List<String> path) throws IOException, FileNotFoundException;

    public boolean update(List<String> oldPath, List<String> newPath);

    public boolean delete(List<String> path);

}
