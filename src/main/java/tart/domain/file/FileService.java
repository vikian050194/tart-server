package tart.domain.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tart.core.examinator.*;
import tart.core.matcher.FileMatcher;
import tart.core.matcher.type.*;

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
    private final FileMatcher gifMatcher = new GifFileMatcher();
    private final FileMatcher mp4Matcher = new Mp4FileMatcher();
    private final List<FileMatcher> matchers = List.of(jpegMatcher, jpgMatcher, pngMatcher, gifMatcher, mp4Matcher);
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
        var dirs = imageRepository.getDirectories(path).stream();
        dirs = dirs.filter(d -> d.startsWith(systemDirPrefix) == showSystemDirs());
        dirs = dirs.sorted();
        return dirs.toList();
    }

    private boolean filterFile(String file, DateFilter filter) {
        var parser = getFileNameParser(file);
        if (parser == null) {
            return false;
        }
        var day = parser.getDay();
        var dayMatches = filter.days.isEmpty() || filter.days.contains(day);
        var month = parser.getMonth();
        var monthMatches = filter.months.isEmpty() || filter.months.contains(month);
        var year = parser.getYear();
        var yearMatches = filter.years.isEmpty() || filter.years.contains(year);
        return dayMatches && monthMatches && yearMatches;
    }

    public List<String> getFiles(List<String> path, DateFilter filter) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getFiles(rootPath, filter);
        }
        var files = imageRepository.getFiles(path).stream();
        files = files.filter(f -> systemMatcher.isMatch(f) == showSystemFiles());
        files = files.filter(f -> matchers.stream().anyMatch(m -> m.isMatch(f)));
        files = files.filter(f -> filterFile(f, filter));
        files = files.sorted(new FileComparator());
        return files.toList();
    }

    private String splitEnd(String source) {
        var chunks = source.split(File.separator);
        return chunks[chunks.length - 1];
    }

    public List<String> getAbsoluteFiles(List<String> path, DateFilter filter) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getAbsoluteFiles(rootPath, filter);
        }
        var files = imageRepository.getAbsoluteFiles(path).stream();
        files = files.filter(f -> systemMatcher.isMatch(splitEnd(f)) == showSystemFiles());
        files = files.filter(f -> matchers.stream().anyMatch(m -> m.isMatch(splitEnd(f))));
        files = files.filter(f -> filterFile(splitEnd(f), filter));
        files = files.sorted(new AbsoluteFileComparator());
        return files.toList();
    }

    // TODO add getDays
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

    // TODO extract common code and re-use in getMonths and getDays
    public List<Integer> getYears(List<String> path, DateFilter filter) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getYears(rootPath, filter);
        }
        var files = getFiles(path, filter);
        var years = new ArrayList<Integer>();
        for (String file : files) {
            var parser = getFileNameParser(file);
            if (parser == null) {
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

    public List<Integer> getMonths(List<String> path, DateFilter filter) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getMonths(rootPath, filter);
        }
        var files = getFiles(path, filter);
        var months = new ArrayList<Integer>(12);
        for (String file : files) {
            var parser = getFileNameParser(file);
            if (parser == null) {
                continue;
            }
            var month = parser.getMonth();
            if (months.contains(month)) {
                continue;
            }
            months.add(month);
        }
        return months.stream().sorted().toList();
    }

    public List<Integer> getDays(List<String> path, DateFilter filter) {
        if (path.isEmpty()) {
            var rootPath = List.of(File.separator);
            return getDays(rootPath, filter);
        }
        var files = getFiles(path, filter);
        var days = new ArrayList<Integer>(31);
        for (String file : files) {
            var parser = getFileNameParser(file);
            if (parser == null) {
                continue;
            }
            var day = parser.getDay();
            if (days.contains(day)) {
                continue;
            }
            days.add(day);
        }
        return days.stream().sorted().toList();
    }

    public void moveByDate(List<String> source, List<String> target, int limit) {
        var count = 0;
        var files = getAbsoluteFiles(source, new DateFilter());
        for (String file : files) {
            System.out.println(file);

            var sourceAbsoluteFile = new ArrayList<String>();
            var chunks = Arrays.asList(file.split(File.separator));
            sourceAbsoluteFile.addAll(chunks.subList(1, chunks.size()));

            System.out.println(sourceAbsoluteFile);

            var fnp = getFileNameParser(splitEnd(file));
            var year = new ArrayList<String>(target);
            year.add(String.valueOf(fnp.getYear()));

            System.out.println(year);

            imageRepository.makeDirectory(year);
            var month = new ArrayList<String>(year);
            month.add(String.format("%02d", fnp.getMonth()));

            System.out.println(month);

            imageRepository.makeDirectory(month);
            var targetAbsoluteFile = new ArrayList<String>(month);
            targetAbsoluteFile.add(splitEnd(file));

            System.out.println(targetAbsoluteFile);

            if (imageRepository.exists(targetAbsoluteFile)) {
                System.out.println("EXISTS!!!!!!");
                return;
            } else {
                var success = imageRepository.move(sourceAbsoluteFile, targetAbsoluteFile);
                if (success) {
                    count++;
                } else {
                    System.out.println("OOPS!!!");
                }
            }

            if (count == limit) {
                return;
            }
        }
    }

    public void renameToLowerCase(List<String> path, int limit) {
        var count = 0;
        var files = getAbsoluteFiles(path, new DateFilter());
        for (String file : files) {
            System.out.println(file);

            var sourceAbsoluteFile = new ArrayList<String>();
            var chunks = Arrays.asList(file.split(File.separator));
            sourceAbsoluteFile.addAll(chunks.subList(1, chunks.size()));

            System.out.println(sourceAbsoluteFile);

            var lastIndex = sourceAbsoluteFile.size() - 1;
            var newName = sourceAbsoluteFile.get(lastIndex).toLowerCase();
            var targetAbsoluteFile = new ArrayList<String>(sourceAbsoluteFile);
            targetAbsoluteFile.set(lastIndex, newName);

            System.out.println(targetAbsoluteFile);

            if (imageRepository.exists(targetAbsoluteFile)) {
                System.out.println("EXISTS!!!!!!");
                continue;
            } else {
                var success = imageRepository.move(sourceAbsoluteFile, targetAbsoluteFile);
                if (success) {
                    count++;
                } else {
                    System.out.println("OOPS!!!");
                }
            }

            if (count == limit) {
                return;
            }
        }
    }

    private String buildPossibleFileName(FileNameParser fnp, int attempt) {
        var result = new StringBuilder();

        result.append(String.format("%04d", fnp.getYear()));
        result.append(String.format("%02d", fnp.getMonth()));
        result.append(String.format("%02d", fnp.getDay()));
        result.append("_");
        result.append(String.format("%02d", fnp.getHour()));
        result.append(String.format("%02d", fnp.getMinute()));
        result.append(String.format("%02d", fnp.getSecond()));

        if (attempt > 0) {
            result.append(String.format("(%d)", attempt));
        }

        result.append(".");
        result.append(fnp.getExtension());

        return result.toString();
    }

    public void renameToRussian(List<String> path, int limit) {
        var count = 0;
        var files = getAbsoluteFiles(path, new DateFilter());
        for (String file : files) {
            System.out.println(file);

            var sourceAbsoluteFile = new ArrayList<String>();
            var chunks = Arrays.asList(file.split(File.separator));
            sourceAbsoluteFile.addAll(chunks.subList(1, chunks.size()));

            System.out.println(sourceAbsoluteFile);

            var lastIndex = sourceAbsoluteFile.size() - 1;

            for (int attempt = 0;; attempt++) {
                var oldName = sourceAbsoluteFile.get(lastIndex);
                var fnp = getFileNameParser(oldName);

                if (fnp instanceof RussianFileNameExaminator.RussianFileNameParser) {
                    System.out.println("ALREADY NORMALIZED");
                    break;
                }

                var newName = buildPossibleFileName(fnp, attempt);
                var targetAbsoluteFile = new ArrayList<String>(sourceAbsoluteFile);
                targetAbsoluteFile.set(lastIndex, newName);

                System.out.println(targetAbsoluteFile);

                if (imageRepository.exists(targetAbsoluteFile)) {
                    System.out.println("EXISTS!!!!!!");
                    continue;
                }

                var success = imageRepository.move(sourceAbsoluteFile, targetAbsoluteFile);
                if (success) {
                    count++;
                } else {
                    System.out.println("OOPS!!!");
                }
                break;
            }

            if (count == limit) {
                return;
            }
        }
    }
}
