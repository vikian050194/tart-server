package tart.domain.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {

    private final FileRepository imageRepository;

    public FileService(FileRepository ir) {
        imageRepository = ir;
    }

    public List<DirectoryInfo> getDirectories() {
        // TODO extract System.getProperty to separate service
        var home = System.getProperty("user.home");
        var homeDirs = home.split(File.separator);
        return getDirectories(List.of(homeDirs));
    }

    public List<DirectoryInfo> getDirectories(List<String> d) {
        return imageRepository.getDirectories(new DirectoryInfo(d));
    }

    public List<FileInfo> getFiles() {
        // TODO extract System.getProperty to separate service
        var home = System.getProperty("user.home");
        var homeDirs = home.split(File.separator);
        return getFiles(List.of(homeDirs));
    }

    public List<FileInfo> getFiles(List<String> d) {
        return imageRepository.getFiles(new DirectoryInfo(d));
    }

    public FileData getFileData(List<String> d, String n) throws IOException {
        return imageRepository.getData(new FileInfo(d, n));
    }

}
