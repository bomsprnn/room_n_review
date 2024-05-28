package homes.has.Controller;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.dto.BuildingsDto;
import homes.has.dto.FavoriteBuildingsDto;
import homes.has.dto.FavoriteDto;
import homes.has.dto.PostDto;
import homes.has.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping("/map/favorite")
@RequiredArgsConstructor
public class FavoriteController{
    private final FavoriteService favoriteService;

    /**
     * 관심 건물 등록 (API no.8)
     **/
    @PostMapping
    public void createFavorite(@RequestPart FavoriteDto favoriteDto) {
        favoriteService.CreateFavorite(favoriteDto.getLocation(), favoriteDto.getMemberId(), favoriteDto.getPosx(), favoriteDto.getPosy());
    }

    /**
     * 관심 건물 삭제 (API no.9)
     **/
    @DeleteMapping
    public void deleteFavorite(@RequestPart FavoriteDto favoriteDto) {
        favoriteService.DeleteFavorite(favoriteDto.getLocation(), favoriteDto.getMemberId());
    }

}
