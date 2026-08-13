package tart.data.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tart.domain.file.*;

public class LocalFileRepository implements FileRepository {

    @Override
    public boolean exists(List<String> path) {
        return Files.exists(Path.of(join(path)), LinkOption.NOFOLLOW_LINKS);
    }

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

    @Override
    public List<String> getAbsoluteFiles(List<String> path) {
        var root = new File(join(path));

        return Stream.of(root.listFiles())
                .filter(f -> f.isFile())
                .map(f -> f.getAbsolutePath())
                .collect(Collectors.toList());
    }

    private String join(List<String> path) {
        return "%s%s".formatted(File.separator, String.join(File.separator, path));
    }

    @Override
    public byte[] getData(List<String> path) throws IOException, FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(join(path), "r");
        byte[] bytes = new byte[(int) raf.length()];
        raf.readFully(bytes);
        return bytes;
    }

    @Override
    public boolean move(List<String> source, List<String> target) {
        var sourcePath = Path.of(join(source));

        if (!exists(source)) {
            return false;
        }

        var targetPath = Path.of(join(target));

        if (exists(target)) {
            return false;
        }

        try {
            Files.move(sourcePath, targetPath, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ex) {
            System.getLogger(LocalFileRepository.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(List<String> path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean makeDirectory(List<String> path) {
        var dirPath = Path.of(join(path));
        if (exists(path)) {
            return false;
        }
        var attrs = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrw-r--"));
        try {
            Files.createDirectory(dirPath, attrs);
        } catch (IOException ex) {
            System.getLogger(LocalFileRepository.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return false;
        }
        return true;
    }
}
