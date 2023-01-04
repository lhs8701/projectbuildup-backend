package projectbuildup.saver.domain.image.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.image.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
