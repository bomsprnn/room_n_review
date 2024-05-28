package homes.has.repository;

import homes.has.domain.Building;
import homes.has.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
   Building findByLocation(String location); //주소로 검색

   List<Building> findByPosxBetweenAndPosyBetween (double minx, double maxx, double miny, double maxy); //위도 x, 경도 y
}
