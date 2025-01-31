package tart.domain.file;

import java.io.*;
import java.util.List;

public interface FileRepository {

    public List<DirectoryInfo> getDirectories();

    public List<DirectoryInfo> getDirectories(DirectoryInfo d);

    public List<FileInfo> getDescriptions(DirectoryInfo d);

    public FileData getData(FileInfo f) throws IOException, FileNotFoundException;

    public boolean update(FileInfo f);

    public boolean delete(FileInfo f);

}
