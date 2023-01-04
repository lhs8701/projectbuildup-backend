package projectbuildup.saver.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.image.entity.Image;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private String fileName; // 저장될 파일 이름
    private String fileOriName; // 원래 파일의 이름
    private String fileUrl; // 저장할 경로

    public Image toEntity(){
        return Image.builder()
                .fileName(this.fileName)
                .fileOriName(this.fileOriName)
                .fileUrl(this.fileUrl)
                .build();
    }
}
