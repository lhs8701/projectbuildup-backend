package projectbuildup.saver.domain.image.service;

import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.image.dto.ImageDto;
import projectbuildup.saver.domain.image.entity.ImageEntity;


public interface ImageService {

    public ImageDto uploadImage(MultipartFile file);

    public boolean removeImage(ImageEntity image);
}
