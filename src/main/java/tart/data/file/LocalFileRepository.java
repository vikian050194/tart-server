package tart.data.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
}
