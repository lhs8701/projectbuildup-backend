package projectbuildup.saver.domain.file.service;

public interface FileService {
    public void makeDirectory(String path);

    public String makeFileName(String fileOriName);
    public String makeFileUrlByDate(String fileName);
}
