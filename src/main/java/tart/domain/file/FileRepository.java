package tart.domain.file;

import java.io.*;
import java.util.List;

public interface FileRepository {

    public boolean exists(List<String> path);

    public List<String> getDirectories(List<String> path);

    public boolean makeDirectory(List<String> path);

    public List<String> getFiles(List<String> path);

    public List<String> getAbsoluteFiles(List<String> path);

    public byte[] getData(List<String> path) throws IOException, FileNotFoundException;

    public boolean move(List<String> source, List<String> target);

    public boolean delete(List<String> path);

}
