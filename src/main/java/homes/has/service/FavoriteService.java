package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.domain.Member;
import homes.has.dto.BuildingsDto;
import homes.has.dto.FavoriteBuildingsDto;
import homes.has.dto.PostDto;
import homes.has.repository.BuildingRepository;
import homes.has.repository.FavoriteRepository;
import homes.has.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService{
    private final FavoriteRepository favoriteRepository;
    private final MemberService memberService;
    private final BuildingService buildingService;
    /**
     * 좋아하는 건물 등록
     **/
    public void CreateFavorite(String location, String memberId, double posx, double posy){
        Member member = memberService.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버 조회 불가능.."));

        Favorite favorite = favoriteRepository.findByLocationAndMember(location, member);
        if (favorite != null){
            throw new IllegalArgumentException("이미 좋아요를 눌렀음");
        }

        favorite = Favorite.builder()
                .location(location)
                .member(member)
                .posx(posx)
                .posy(posy)
                .build();

        member.getFavorites().add(favorite);
        favoriteRepository.save(favorite);
    }

    /**
     * 좋아하는 건물 등록 취소
     **/
    public void DeleteFavorite (String location, String memberId) {
        Member member = memberService.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버 조회 불가능.."));

        Favorite favorite = favoriteRepository.findByLocationAndMember(location, member);
        if (favorite == null) {
            throw new IllegalArgumentException("좋아요를 조회할 수 없음");
        }
        member.getFavorites().remove(favorite);
        favoriteRepository.delete(favorite);
    }

    /**
     * 좋아요 누른 건물 조회
     **/
    public List<FavoriteBuildingsDto> GetFavoriteBuildings(String memberId){
        Member member = memberService.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없음"));
        List<Favorite> favorites = member.getFavorites();
        List<FavoriteBuildingsDto> favoriteBuildingsDtos = new ArrayList<>();

        for (Favorite favorite : favorites) { //좋아요 누른 항목에서 building 테이블 존재 여부 조회
            Building building = buildingService.findByLocation(favorite.getLocation());
            if (building != null) { // 빌딩에 해당 주소를 가진 내역 존재 시

                Double totalGrade = building.getTotalgrade();
                int reviewCount = building.getReviews().size();
                FavoriteBuildingsDto favoriteBuildingsDto = new FavoriteBuildingsDto(
                        building.getId(),
                        building.getLocation(),
                        building.getPosx(),
                        building.getPosy(),
                        totalGrade,
                        reviewCount,
                        true
                        , LocalDateTime.now()
                );
                favoriteBuildingsDtos.add(favoriteBuildingsDto);
            }else{
                FavoriteBuildingsDto favoriteBuildingsDto = new FavoriteBuildingsDto(
                        null,
                        favorite.getLocation(),
                        favorite.getPosx(),
                        favorite.getPosy(),
                        0.0,
                        0,
                        true
                        ,LocalDateTime.now()
                );
                favoriteBuildingsDtos.add(favoriteBuildingsDto);

            }
        }
        favoriteBuildingsDtos.sort(Comparator.comparing(FavoriteBuildingsDto::getCreatedAt).reversed());

        return favoriteBuildingsDtos;
    }

    public boolean existsByLocationAndMemberId(String location, String memberId) {
        return favoriteRepository.existsByLocationAndMemberId(location, memberId);
    }

}
