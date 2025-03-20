package tart.domain.file;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import tart.core.examinator.EnglishFileNameExaminator;
import tart.core.examinator.FileNameExaminator;
import tart.core.examinator.FileNameParser;
import tart.core.examinator.RussianFileNameExaminator;
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

    public List<String> getDirectories(List<String> path) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getDirectories(rootPath);
        }
        var systemDirPrefix = ".";
        var dirs = imageRepository.getDirectories(path);
        var filteredDirs = dirs.stream().filter(d -> d.startsWith(systemDirPrefix) == showSystemDirs()).toList();
        return filteredDirs;
    }

    public List<String> getFiles(List<String> path) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getFiles(rootPath);
        }
        var files = imageRepository.getFiles(path).stream();
        files = files.filter(f -> systemMatcher.isMatch(f) == showSystemFiles());
        files = files.filter(f -> matchers.stream().anyMatch(m -> m.isMatch(f)));
        return files.toList();
    }

    // TODO add getPossibleMonths
    // TODO add getPossibleDays
    // TODO add getAvailableYears or store state on client side and use simple getYears for root filter-free state and for non-root filtered state?
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

    private FileNameParser getFileNameParser(String file) {
        var englishExaminator = new EnglishFileNameExaminator();
        var russianExaminator = new RussianFileNameExaminator();
        var examinators = List.of(englishExaminator, russianExaminator);
        // TODO is generic examinator necessary class structure?
        for (FileNameExaminator<? extends FileNameParser> examinator : examinators) {
            if (examinator.isMatch(file)) {
                return examinator.getNameParser(file);
            }
        }
        // TODO Optional or null?
        return null;
    }

    public List<Integer> getYears(List<String> path) {
        return getYears(path, new DateFilter());
    }

    public List<Integer> getYears(List<String> path, DateFilter filter) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getYears(rootPath, filter);
        }
        var files = getFiles(path);
        var years = new LinkedList<Integer>();
        for (String file : files) {
            var parser = getFileNameParser(file);
            if (parser == null) {
                continue;
            }
            var day = parser.getDay();
            if (!(filter.days.isEmpty() || filter.days.contains(day))) {
                continue;
            }
            var month = parser.getMonth();
            if (!(filter.months.isEmpty() || filter.months.contains(month))) {
                continue;
            }
            var year = parser.getYear();
            if (years.contains(year)) {
                continue;
            }
            years.add(year);
        }
        return years.stream().sorted().toList();
    }

}
