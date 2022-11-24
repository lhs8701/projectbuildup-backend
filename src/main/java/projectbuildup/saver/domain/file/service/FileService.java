package projectbuildup.saver.domain.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public void makeDirectory(String path);

    public String makeFileName(String fileOriName);

    public String makeFileUrlByDate(String fileName);

    public void transferFile(MultipartFile file, String transferUrl);
}