package tart.domain.file;

import java.io.IOException;
import java.util.List;

public class FileService {

    private final FileRepository imageRepository;

    public FileService(FileRepository ir) {
        imageRepository = ir;
    }

    public List<DirectoryInfo> getDirectories() {
        return imageRepository.getDirectories();
    }

    public List<DirectoryInfo> getDirectories(List<String> d) {
        return imageRepository.getDirectories(new DirectoryInfo(d));
    }

    public List<FileInfo> getDescriptions(List<String> d) {
        return imageRepository.getDescriptions(new DirectoryInfo(d));
    }

    public FileData getFileData(List<String> d, String n) throws IOException {
        return imageRepository.getData(new FileInfo(d, n));
    }

}
