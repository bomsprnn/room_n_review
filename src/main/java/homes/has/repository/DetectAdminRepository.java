package homes.has.repository;

import homes.has.domain.DetectAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectAdminRepository extends JpaRepository<DetectAdmin,Long> {


    public boolean existsByMemberId(String memberId);
}
