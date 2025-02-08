package tart.domain.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {

    private final FileRepository imageRepository;

    public FileService(FileRepository ir) {
        imageRepository = ir;
    }

    public List<String> getDirectories() {
        // TODO extract System.getProperty to separate service
        var home = System.getProperty("user.home");
        var homeDirPath = List.of(home.split(File.separator));
        return getDirectories(homeDirPath);
    }

    public List<String> getDirectories(List<String> path) {
        // TODO extract following flag to properties or UI
        var showSystemDirs = false;
        var systemDirPrefix = ".";
        var dirs = imageRepository.getDirectories(path);
        var filteredDirs = dirs.stream().filter(d -> d.startsWith(systemDirPrefix) == showSystemDirs).toList();
        return filteredDirs;
    }

    public List<String> getFiles() {
        // TODO extract System.getProperty to separate service
        var home = System.getProperty("user.home");
        var homeDirPath = List.of(home.split(File.separator));
        return getFiles(homeDirPath);
    }

    public List<String> getFiles(List<String> path) {
        return imageRepository.getFiles(path);
    }

    public byte[] getFileData(List<String> path) throws IOException {
        return imageRepository.getData(path);
    }

}
