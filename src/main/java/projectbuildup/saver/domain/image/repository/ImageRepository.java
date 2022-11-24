package projectbuildup.saver.domain.image.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.image.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
