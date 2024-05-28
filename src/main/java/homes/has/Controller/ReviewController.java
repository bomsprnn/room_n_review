package homes.has.Controller;

import homes.has.domain.*;
import homes.has.dto.*;
import homes.has.enums.Valid;
import homes.has.repository.BuildingRepository;
import homes.has.repository.FavoriteRepository;
import homes.has.repository.ReviewRepository;
import homes.has.service.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class ReviewController{
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ImageFileService imageFileService;
    private final ReviewImageFileService reviewImageFileService;
    /** 테스트용 코드 삭제 필
     *
     *
     *
     *
     * **/
    private final FavoriteService favoriteService;
    private final BuildingService buildingService;
    /** 테스트용 코드 삭제 필
     *
     *
     *
     *
     * **/
//


    /*
     * 빌딩 리스트 반환 (API no.1,2)

    @GetMapping("/building")
    public List<Building> getBuildingList(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double distance) {
        List<Building> buildingList = reviewService.GetBuildingsByLocation(latitude, longitude, distance);
        return buildingList;
    } */

    /**
     * 빌딩 리스트 반환 (API no.1,2)
     **/
    @GetMapping("/building")
    public List<BuildingsDto> getBuildingList(@RequestParam double lat, @RequestParam double lon, @RequestParam String memberId  ) {
        List<BuildingsDto> buildingList = reviewService.GetBuildingsForMap(lat, lon,42.00, memberId);
        return buildingList;
    }

    /** 테스트용 코드 삭제 필
     *
     *
     *
     *
     * **/
    @GetMapping("/test")
    public List<BuildingsDto> getBuildingList() {
        List<Building> buildings = new ArrayList<>();
        buildings.addAll(buildingService.findAll());
        List<BuildingsDto> buildingsDtos = new ArrayList<>();
        for (Building building : buildings) {
            int reviewCount = building.getReviews().size();
            boolean isLiked = favoriteService.existsByLocationAndMemberId(building.getLocation(),"ABC");
            boolean reviewAuth = reviewService.reviewWriteAuthority(building.getLocation(),"ABC");
            buildingsDtos.add(new BuildingsDto(building.getId(), building.getLocation(), building.getPosx(), building.getPosy(), building.getTotalgrade(),reviewCount, isLiked,reviewAuth));
        }
        return buildingsDtos;
    }
    /**
     *
     *
     *
     *
     * **/



    /**
     * 빌딩 상세 페이지 반환
     **/
    @GetMapping("/building/{location}")
    public BuildingsDto getBuildingList(@PathVariable String location, @RequestPart String memberId) {
        BuildingsDto buildingsDto = reviewService.getBuildingDtoByLocation(location,memberId);
        return buildingsDto;
    }


    /**
     * 특정 건물 리뷰 리스트 반환 (API no.3)
     **/
//    @GetMapping("/{location}/detail")
//    public List<Review> getReviewList(@PathVariable("location") String location) {
//        List<Review> reviewList = reviewService.GetReviewList(location);
//        return reviewList;
//    }

    /*
    * 이미지 출력을 위해 임시 수정 했습니다.
    **/
    @GetMapping("/{location}/review")
    public List<ReviewDto> getReviewList(@PathVariable("location") String location) {
        List<Review> reviewList = reviewService.GetReviewList(location);
        List<ReviewDto> reviewDtos = new ArrayList<>();
        if (reviewList!=null) {
            for (Review review : reviewList) {
                List<ResponseEntity<byte[]>> images = new ArrayList<>();
                if(review.getReviewImageFiles()!=null){
                    for (ReviewImageFile reviewImageFile : review.getReviewImageFiles()) {
                        ImageFile imageFile = reviewImageFile.getImageFile();
                        images.add(imageFileService.printFile(imageFile));
                    }
                }
                ReviewDto reviewDto = ReviewDto.builder()
                        .id(review.getId())
                        .location(location)
                        .memberId(review.getMember().getId())
                        .createdAt(review.getCreatedAt())
                        .modifiedAt(review.getModifiedAt())
                        .grade(review.getGrade())
                        .body(review.getBody())
                        .buildingId(review.getBuilding().getId())
                        .images(images)
                        .build();
                reviewDtos.add(reviewDto);
            }
        }reviewDtos.sort(Comparator.comparing(ReviewDto::getCreatedAt).reversed());

        return reviewDtos;
    }


    /**
     * 리뷰 작성 시도시 권한 boolean 반환 ( 리뷰 서비스 코드로 이동하여 컨트롤러 /building에 편입 )

// 추후 검토 필
    @GetMapping("/{location}/review/write")
    public Boolean reviewWriteAuthority(@PathVariable String location,@RequestParam String memberId){
        Member member = memberService.findById(memberId).get();

        if(member.getValid()== Valid.UNCERTIFIED)
            return false;
        else if(member.getLocation()!= location)
            return false;
        else if(reviewService.existsByMemberIdAndLocation(memberId,location))
            return false;
        else return
            true;
    }**/

/*
*   리뷰 작성
* */
    @PostMapping("/{location}/review/write")
    public void createReview(@RequestPart CreateReviewDto createReviewDto,
                             @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        reviewService.CreateReview(createReviewDto.getMemberId(), createReviewDto.getLocation(), createReviewDto.getGrade(),
                createReviewDto.getBody(), createReviewDto.getPosx(), createReviewDto.getPosy(), files);

    }

    /**
     * 리뷰 수정 폼 생성 (API no.5)
     **/
    @GetMapping("/{location}/review/{reviewId}/modify")
    public ReviewDto getReviewById(@PathVariable("location") String location, @PathVariable("reviewId") Long id) {
        Review review = reviewService.getReviewById(id);
        List<ResponseEntity<byte[]>> images = new ArrayList<>();
        if(review.getReviewImageFiles()!=null) {
            for (ReviewImageFile reviewImageFile : review.getReviewImageFiles()) {
                ImageFile imageFile = reviewImageFile.getImageFile();
                images.add(imageFileService.printFile(imageFile));
            }

        }
        return ReviewDto.builder()
                .id(review.getId())
                .location(location)
                .memberId(review.getMember().getId())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .grade(review.getGrade())
                .body(review.getBody())
                .buildingId(review.getBuilding().getId())
                .images(images)
                .build();
    }

    /**
     * 리뷰 수정 (API no.6)
     **/
    @PostMapping("/{location}/review/{reviewId}/modify")
    public void updateReview(@PathVariable("reviewId") Long id, @RequestPart UpdateReviewRequest request,
                             @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        ReviewBody body = request.getBody();
        ReviewGrade grade = request.getGrade();
        reviewService.UpdateReview(id, grade, body,files);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateReviewRequest {
        private ReviewBody body;
        private ReviewGrade grade;
    }

    /**
     * 리뷰 삭제 (API no.7)
     **/
    @DeleteMapping("/{location}/review/{reviewId}")
    public void deleteReview( @PathVariable("reviewId") Long id) {
        /*
       * review 삭제시 db에 멤버의 review가 없으면 uncertified로 갱신
       * */
       Review review = reviewService.getReviewById(id);
       Member member = review.getMember();

       if (review.getReviewImageFiles()!= null) {
           //이미지 파일 삭제
           for (ReviewImageFile reviewImageFile : review.getReviewImageFiles()) {
               reviewImageFileService.delete(reviewImageFile.getId());
               imageFileService.delete(reviewImageFile.getImageFile());
           }
       }

        reviewService.DeleteReview(id);
    }
}
