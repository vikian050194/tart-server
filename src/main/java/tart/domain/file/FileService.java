package tart.domain.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {

    private final FileRepository imageRepository;

    public FileService(FileRepository ir) {
        imageRepository = ir;
    }

    public boolean showSystemDirs() {
        // TODO extract following flag to properties or UI
        return false;
    }

    public boolean showSystemFiles() {
        // TODO extract following flag to properties or UI
        return false;
    }

    public List<String> getDirectories() {
        var rootPath = List.of(File.separator);
        return getDirectories(rootPath);
    }

    public List<String> getDirectories(List<String> path) {
        var systemDirPrefix = ".";
        var dirs = imageRepository.getDirectories(path);
        var filteredDirs = dirs.stream().filter(d -> d.startsWith(systemDirPrefix) == showSystemDirs()).toList();
        return filteredDirs;
    }

    public List<String> getFiles() {
        var rootPath = List.of(File.separator);
        return getFiles(rootPath);
    }

    public List<String> getFiles(List<String> path) {
        var systemFilePrefix = ".";
        var files = imageRepository.getFiles(path);
        // TODO add filtering
        var filteredFiles = files.stream().filter(d -> d.startsWith(systemFilePrefix) == showSystemFiles() && (d.endsWith("jpg") || d.endsWith("jpeg") || d.endsWith("png"))).toList();
        return filteredFiles;
    }

    public byte[] getFileData(List<String> path) throws IOException {
        return imageRepository.getData(path);
    }

}
