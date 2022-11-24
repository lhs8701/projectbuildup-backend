package projectbuildup.saver.domain.image.service;

import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.image.dto.ImageDto;


public interface ImageService {

    public ImageDto uploadImage(MultipartFile file);

    public String makeFileName(String fileOriName);

    public String makeFileUrl(String fileName, String uploadPath);

    public boolean deleteImage(String fileUrl);

    void save(ImageDto image);

}
