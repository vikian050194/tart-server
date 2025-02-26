package tart.domain.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import tart.core.matcher.FileMatcher;
import tart.core.matcher.type.JpegFileMatcher;
import tart.core.matcher.type.JpgFileMatcher;
import tart.core.matcher.type.Mp4FileMatcher;
import tart.core.matcher.type.PngFileMatcher;
import tart.core.matcher.type.SystemFileMatcher;

public class FileService {

    public enum ScanMode {
        STRICT, RECURSIVE
    };

    public enum FileType {
        JPEG, PNG, MP4
    };

    private final FileRepository imageRepository;
    private final FileMatcher jpegMatcher = new JpegFileMatcher();
    private final FileMatcher jpgMatcher = new JpgFileMatcher();
    private final FileMatcher pngMatcher = new PngFileMatcher();
    private final FileMatcher mp4Matcher = new Mp4FileMatcher();
    private final List<FileMatcher> matchers = List.of(jpegMatcher, jpgMatcher, pngMatcher, mp4Matcher);
    private final FileMatcher systemMatcher = new SystemFileMatcher();

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
        var files = imageRepository.getFiles(path).stream();
        files = files.filter(f -> systemMatcher.isMatch(f) == showSystemFiles());
        files = files.filter(f -> matchers.stream().anyMatch(m -> m.isMatch(f)));
        return files.toList();
    }

    // TODO add getPossibleYears
    // TODO add getPossibleMonths
    // TODO add getPossibleDays
    // TODO add getAvailableYears
    // TODO add getAvailableMonths
    // TODO add getAvailableDays
    public byte[] getFileData(List<String> path) throws IOException {
        return imageRepository.getData(path);
    }

    public FileType getFileType(List<String> path) throws UnsupportedOperationException {
        var name = path.get(path.size() - 1);
        if (jpegMatcher.isMatch(name) || jpgMatcher.isMatch(name)) {
            return FileType.JPEG;
        }
        if (pngMatcher.isMatch(name)) {
            return FileType.PNG;
        }
        if (mp4Matcher.isMatch(name)) {
            return FileType.MP4;
        }
        throw new UnsupportedOperationException(String.format("%s has unsupperted file type.", name));
    }

}
