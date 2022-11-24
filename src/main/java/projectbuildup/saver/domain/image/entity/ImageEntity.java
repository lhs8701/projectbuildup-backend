package projectbuildup.saver.domain.image.entity;

import lombok.*;
import projectbuildup.saver.domain.user.entity.UserEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName; // 저장될 파일 이름

    private String fileOriName; // 원래 파일의 이름

    private String fileUrl; // 저장할 경로

    @OneToOne(mappedBy = "profileImage", fetch = FetchType.LAZY)
    private UserEntity uploader;
}
