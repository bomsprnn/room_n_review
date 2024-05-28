package homes.has.repository;

import homes.has.domain.LocRequest;
import homes.has.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Query("SELECT r FROM Review r JOIN FETCH r.member m WHERE m.id = :memberId")
    public List<Review> findByMemberId(@Param("memberId") String memberId);

    public boolean existsByMemberIdAndLocation(String memberId, String location);
    public boolean existsByMemberId(String memberId);

    public List<Review> findAll();

    public List<Review> findByBuildingId(Long buildingId);

    @Query("SELECT r FROM Review r WHERE r.location = :location")
    public List<Review> findByLocation(String location);
}
