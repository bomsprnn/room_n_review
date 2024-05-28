package homes.has.repository;

import homes.has.domain.LikePosts;
import homes.has.domain.LocRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocRequestRepository extends JpaRepository<LocRequest,Long> {

    @Query("SELECT lr FROM LocRequest lr JOIN FETCH lr.member m WHERE m.id = :memberId")
    public LocRequest findByMemberId(@Param("memberId") String memberId);

}
