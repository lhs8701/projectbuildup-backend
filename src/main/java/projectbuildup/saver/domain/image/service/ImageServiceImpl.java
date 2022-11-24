package projectbuildup.saver.domain.image.service;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.file.service.FileService;
import projectbuildup.saver.domain.image.dto.ImageDto;
import projectbuildup.saver.domain.image.entity.ImageEntity;
import projectbuildup.saver.global.error.exception.CWrongFileTypeException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{



    private final FileService fileService;


    @Override
    public ImageDto uploadImage(MultipartFile file) {
        //        이미지가 없거나, 잘못된 형식일 경우
        if (file.isEmpty() || !file.getContentType().startsWith("image")) {
            throw new CWrongFileTypeException();
        }

        String fileOriName = Optional.ofNullable(file.getOriginalFilename()).orElse("image");
        String fileName = makeFileName(fileOriName);
        String fileUrl = makeFileUrl(fileName);
        if (root == null)
            root = "";
        String transferUrl = root + fileUrl;
        Path savePath = Paths.get(transferUrl);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ImageDto(fileName, fileOriName, fileUrl);
    }

    @Override
    public boolean removeImage(ImageEntity image) {
        return false;
    }
}
