package homes.has.repository;

import homes.has.domain.PostImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageFileRepository extends JpaRepository<PostImageFile, Long> {
}
