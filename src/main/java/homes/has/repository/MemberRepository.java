package homes.has.repository;

import homes.has.domain.LocRequest;
import homes.has.domain.Member;
import homes.has.domain.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsById(String String);
}
