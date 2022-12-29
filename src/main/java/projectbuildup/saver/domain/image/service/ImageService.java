package projectbuildup.saver.domain.image.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.file.error.exception.CFileNotFoundException;
import projectbuildup.saver.domain.file.service.FileService;
import projectbuildup.saver.domain.image.dto.ImageDto;
import projectbuildup.saver.domain.image.entity.Image;
import projectbuildup.saver.domain.file.error.exception.CWrongFileTypeException;

import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageService {

    private final FileService fileService;

    @Value("${location.root}")
    private String ROOT_PATH;

    public ImageDto uploadImage(MultipartFile file) {
        //이미지가 없거나, 잘못된 형식일 경우 예외 발생
        if (file.isEmpty() || !file.getContentType().startsWith("image")) {
            throw new CWrongFileTypeException();
        }

        String fileOriName = Optional.ofNullable(file.getOriginalFilename()).orElse("image");
        String fileName = fileService.makeFileName(fileOriName);
        String fileUrl = fileService.makeFileUrlByDate(fileName);
        fileService.transferFile(file, ROOT_PATH + fileUrl);

        return new ImageDto(fileName, fileOriName, fileUrl);
    }

    public void removeImage(Image image) {
        String fileUrl = image.getFileUrl();
        File file = new File(ROOT_PATH + fileUrl);

        if (!file.exists()) {
            throw new CFileNotFoundException();
        }
        if (file.delete()) {
            log.info("이미지 삭제 성공");
            return;
        }
        log.info("이미지 삭제 실패");
    }
}
