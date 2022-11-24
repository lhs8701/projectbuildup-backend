package projectbuildup.saver.domain.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${location.root}")
    private String ROOT_PATH;

    @Value("${location.image-dir}")
    private String MEDIA_PATH;

    @Override
    public void makeDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public String makeFileName(String fileOriName) {
        int idx = fileOriName.lastIndexOf(".");
        String ext = fileOriName.substring(idx);
        String uuid = UUID.randomUUID().toString();
        return uuid + ext;
    }

    @Override
    public String makeFileUrlByDate(String fileName) {
        String str_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fileUrl = MEDIA_PATH + str_date + "/" + fileName;
        String folderPath = ROOT_PATH + fileUrl;
        makeDirectory(folderPath);

        return fileUrl;
    }

    public void transferFile(MultipartFile file, String transferUrl) {
        Path savePath = Paths.get(transferUrl);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
