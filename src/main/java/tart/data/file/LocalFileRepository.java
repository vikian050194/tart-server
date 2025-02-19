package tart.data.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.core.logger.Logger;
import tart.core.matcher.FileMatcher;
import tart.domain.file.*;

public class LocalFileRepository implements FileRepository {

    @Override
    public List<String> getDirectories(List<String> path) {
        var root = new File(join(path));

        return Stream.of(root.listFiles())
                .filter(f -> f.isDirectory())
                .map(d -> d.getName())
                .collect(Collectors.toList());

    }

    @Override
    public List<String> getFiles(List<String> path) {
        var root = new File(join(path));

        return Stream.of(root.listFiles())
                .filter(f -> f.isFile())
                .map(f -> f.getName())
                .collect(Collectors.toList());
    }

    private String join(List<String> path) {
        return "/%s".formatted(String.join(File.separator, path));
    }

    @Override
    public byte[] getData(List<String> path) throws IOException, FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(join(path), "r");
        byte[] bytes = new byte[(int) raf.length()];
        raf.readFully(bytes);
        return bytes;
    }

    @Override
    public boolean update(List<String> oldPath, List<String> newPath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(List<String> path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private final ArrayList<File> files = new ArrayList<>();
    private boolean changed = false;

    private List<File> listFiles(File dir, FileMatcher fileMatcher) {
        var result = new ArrayList<File>();
        var queue = new LinkedList<File>();
        queue.add(dir);

        while (!queue.isEmpty()) {
            var currentDir = queue.poll();
            var dirs = Stream.of(currentDir.listFiles())
                    .filter(file -> file.isDirectory())
                    .collect(Collectors.toList());
            queue.addAll(dirs);
            var currentDirFiles = Stream.of(currentDir.listFiles())
                    .filter(file -> !file.isDirectory() && fileMatcher.isMatch(file.getName()))
                    .collect(Collectors.toList());
            result.addAll(currentDirFiles);
        }

        return result;
    }

    public boolean inspect(File dir, List<FileMatcher> matchers) {
        if (!dir.exists()) {
            Logger.getLogger().warning(String.format("%s is not found", dir));
            return false;
        }

        if (dir.isFile()) {
            Logger.getLogger().warning(String.format("%s is not directory", dir));
            return false;
        }

        files.clear();

        for (FileMatcher matcher : matchers) {
//            files.addAll(listFiles(dir, matcher).stream().map((f) -> matcher.wrap(f)).toList());
        }

//        files.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));

        return !files.isEmpty();
    }

//    public List<FileWrapper> getFiles() {
//        if (changed) {
//            // TODO refactor this non optimal last file mather storing
//            // TODO full inspect is heavy - update only changed File?
//        }
//
//        return files;
//    }
    public File moveTo(File sourceFile, File targetDir) {
        var targetFile = new File(targetDir, sourceFile.getName());

        changed = !sourceFile.equals(targetFile);

        if (changed) {
            sourceFile.renameTo(targetFile);
            return targetFile;
        }

        return sourceFile;
    }

//    public void delete(FileWrapper targetFile) {
        // TODO what is safest way to remove T instance from ArrayList<T> where T is class?
//        files.remove(targetFile);
//        targetFile.getFile().delete();
//    }

    public boolean inspect(DirectoryInfo dir, List<FileMatcher> matchers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
