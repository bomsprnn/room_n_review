package homes.has.service;

import homes.has.domain.Building;
import homes.has.domain.Favorite;
import homes.has.domain.Member;
import homes.has.enums.Valid;
import homes.has.repository.BuildingRepository;
import homes.has.repository.FavoriteRepository;
import homes.has.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class FavoriteServiceTest{

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private FavoriteService favoriteService;

    @Test
    public void testFavorite(){
        //given
        String location = "가좌대로 11번길 1";
        String location2 = "가좌대로 11번길 2";


        Member member = new Member();
        Member member2 = new Member();
        memberRepository.save(member);
        memberRepository.save(member2);


       /*
        Favorite favorite = new Favorite();
        favorite.setLocation(location);
        favorite.setMember(member);

        Favorite favorite2 = new Favorite();
        favorite2.setLocation(location2);
        favorite2.setMember(member2);*/

        // when
        favoriteService.CreateFavorite(location,member.getId());
        favoriteService.CreateFavorite(location2,member.getId());
        favoriteService.CreateFavorite(location,member2.getId());

        // then
        //Favorite favorite = favoriteRepository.findByLocationAndMember(location,member);
        assertEquals(2,member.getFavorites().size());

        System.out.println(member.getFavorites().size());
        System.out.println(favoriteService.GetFavoriteBuildings(member.getId()));
       // Favorite favorite2 = favoriteRepository.findByLocationAndMember(location,member2);
        //assertEquals(member2.getId(), favorite2.getMember().getId());

       // assertEquals(2, favoriteService.GetFavoriteBuildings(member.getId()).size());
    }

}