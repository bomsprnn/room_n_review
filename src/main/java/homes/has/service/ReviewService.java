package homes.has.service;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import homes.has.domain.*;
import homes.has.dto.BuildingsDto;

import homes.has.enums.FilePath;
import homes.has.enums.Valid;
import homes.has.repository.BuildingRepository;
import homes.has.repository.FavoriteRepository;
import homes.has.repository.MemberRepository;
import homes.has.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final FavoriteService favoriteService;
    private final MemberService memberService;
    private final ImageFileService imageFileService;
    private final ReviewImageFileService reviewImageFileService;
    private final BuildingService buildingService;

    private final BuildingRepository buildingRepository;
   // private final AmazonS3 amazonS3;
    private static final double EARTH_RADIUS = 6371; // 지구 반경(km)
    //private static final String UPLOAD_DIR = "/path/"; // 로컬에서 경로
    private static final String BUCKET_NAME = "homes-admin"; // S3버킷 이름
    /**
     * 리뷰 생성
     **/
//    public void CreateReview (String memberId, String location, ReviewGrade grade, ReviewBody body, double posx, double posy, List<MultipartFile> files) throws IOException {
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없음"));
//        Building building = buildingRepository.findByName(location);
//        if (building == null) {//빌딩 테이블에 location이 존재하지 않으면 추가
//            building = new Building(location,posx,posy);
//        }
//
//        int countReview = building.getReviews().size(); //빌딩이 가지고있는 리뷰 수 확인
//        Double newTotalGrade = updateTotalGrade(building.getTotalgrade(), countReview,null, grade);
//        building.setTotalgrade(newTotalGrade); //빌딩 테이블의 total grade 받아와서 새로운 review 반영
//
//
//
//        Review review = Review.builder()
//                .grade(grade)
//                .body(body)
//                .building(building)
//                .location(building.getName())
//                .member(member)
//                .reviewImageFiles(new ArrayList<>())
//                .build();
//
////      review entity에 이미지 추가, imageFileService에서 entity를 가져오는과정, for 문 내부의
////      1,2line에서 해당 객체의 id 값이 null이 아닌지 확인 할 필요가 있음
//        if(files != null) {
//            for (MultipartFile multipartFile : files) {
//                ImageFile imageFile = imageFileService.saveFile(multipartFile, FilePath.REVIEW);
//                ReviewImageFile reviewImageFile = reviewImageFileService.save(new ReviewImageFile(review, imageFile));
//                review.getReviewImageFiles().add(reviewImageFile);
//            }
//        }
//
//
//        reviewRepository.save(review);
//
//        building.getReviews().add(review);
//
//        buildingRepository.save(building);
//
////      member valid 변경
//        memberService.changeValid(member, Valid.CERTIFIED);
//    }
//

    public Long CreateReview (String memberId, String location, ReviewGrade grade, ReviewBody body, double posx, double posy, List<MultipartFile> files) throws IOException {
        Member member = memberService.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없음"));
        Building building = buildingService.findByLocation(location);
        if (building == null) {
            building = buildingService.save(location, posx, posy);
        }
        int countReview = building.getReviews().size(); //빌딩이 가지고있는 리뷰 수 확인
        Double newTotalGrade = updateTotalGrade(building.getTotalgrade(), countReview,null, grade);
        building.setTotalgrade(newTotalGrade); //빌딩 테이블의 total grade 받아와서 새로운 review 반영

        Review review = Review.builder()
                .grade(grade)
                .body(body)
                .building(building)
                .location(location)
                .member(member)
                .reviewImageFiles(new ArrayList<>())
                .build();

//      review entity에 이미지 추가, imageFileService에서 entity를 가져오는과정, for 문 내부의
//      1,2line에서 해당 객체의 id 값이 null이 아닌지 확인 할 필요가 있음
        if(files != null) {
            for (MultipartFile multipartFile : files) {
                ImageFile imageFile = imageFileService.saveFile(multipartFile, FilePath.REVIEW);
                ReviewImageFile reviewImageFile = reviewImageFileService.save(new ReviewImageFile(review, imageFile));
                review.getReviewImageFiles().add(reviewImageFile);
            }
        }


        Long reviewId = reviewRepository.save(review).getId();

//      member valid 변경
        return reviewId;
    }

    /**
     * 리뷰 삭제
     **/
    public void DeleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));

        Building building = review.getBuilding(); //리뷰에 포함된 빌딩 정보 수정

        double newTotalGrade = updateTotalGrade(building.getTotalgrade(), building.getReviews().size(), review.getGrade(), null); //null은 삭제된 리부
        buildingService.setTotalGrade(building, newTotalGrade); // 새로운 총 별점으로 업데이트

//        확인 필요, 연관관계 주인 review에서 삭제를 통해 자동으로 삭제될것으로 예상
//        building.getReviews().remove(review);
        reviewRepository.delete(review); // 리뷰 삭제

        if (building.getReviews().size() == 1) {
            buildingService.delete(building.getId());
        }
    }

    /**
     * 리뷰 수정
     **/
    public void UpdateReview(Long id, ReviewGrade grade, ReviewBody body, List<MultipartFile> files) throws IOException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));

        Building building = review.getBuilding();
        double newTotalGrade = updateTotalGrade(building.getTotalgrade(), building.getReviews().size(), review.getGrade(), grade);
        buildingService.setTotalGrade(building, newTotalGrade);

//      기존의 이미지 파일 삭제
        if(review.getReviewImageFiles()!=null) {
            for (ReviewImageFile reviewImageFile : review.getReviewImageFiles()) {
                imageFileService.delete(reviewImageFile.getImageFile());
                reviewImageFileService.delete(reviewImageFile.getId());
            }
        }

        if(files!= null) {
//        받은 새로운 파일 등록
            for (MultipartFile multipartFile : files) {
                ImageFile imageFile = imageFileService.saveFile(multipartFile, FilePath.REVIEW);
                ReviewImageFile reviewImageFile = reviewImageFileService.save(new ReviewImageFile(review, imageFile));
//            review.reviewImageFile null일 경우 에러 가능

                review.getReviewImageFiles().add(reviewImageFile);
            }
        }
        review.setGrade(grade);
        review.setBody(body);
        reviewRepository.save(review); //리뷰 수정 완
    }

    /**
     * 상세 리뷰 정보 출력
     **/
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없음"));
    }

    /**
     * 특정 건물 리뷰 LIST 반환
     **/
    public List<Review> GetReviewList(String location){
        return reviewRepository.findByLocation(location);
//
//        if (building == null) {
//            throw new IllegalArgumentException("찾을수업슴..");
//        }
//        return building.getReviews();
    }

    /**거리만큼 away한 위-경도값을 반환하는 메서드**/
    public static double[] getBoundingBox(double latitude, double longitude, double distance) {
        double radianDistance = distance / EARTH_RADIUS;
        double radianLatitude = Math.toRadians(latitude);
        double radianLongitude = Math.toRadians(longitude);

        double minLat = radianLatitude - radianDistance;
        double maxLat = radianLatitude + radianDistance;

        double deltaLongitude = Math.asin(Math.sin(radianDistance) / Math.cos(radianLatitude));
        double minLng = radianLongitude - deltaLongitude;
        double maxLng = radianLongitude + deltaLongitude;

        double[] boundingBox = {Math.toDegrees(minLat), Math.toDegrees(minLng), Math.toDegrees(maxLat), Math.toDegrees(maxLng)};
        return boundingBox;
    }

    /** 이것은 ... 리뷰 CRUD 기능에 쓰이는 메서드... **/
    private double updateTotalGrade(double totalGrade, int countReview, ReviewGrade oldGrade, ReviewGrade newGrade) {
        double sum = totalGrade * countReview;
        if (oldGrade != null) { //수정or삭제인 경우
            sum -= oldGrade.getLessor() + oldGrade.getArea() + oldGrade.getQuality() + oldGrade.getNoise();
        }
        if (newGrade != null) { //생성or수정인 경우
            sum += newGrade.getLessor() + newGrade.getArea() + newGrade.getQuality() + newGrade.getNoise();
        }
        int reviewCount = countReview + (newGrade == null ? 0 : 1) - (oldGrade == null ? 0 : 1);
        if (reviewCount == 0) {
            return 0.0;
        }
        return sum / reviewCount; //삭제이면 0-1, 생성이면 1-0, 수정이면 1-1
    }

    public BuildingsDto getBuildingDtoByLocation(String location,String memberId) {
        Building building = buildingRepository.findByLocation(location);
        if (building == null) {
            throw new IllegalArgumentException("빌딩이 존재하지 않음");
        }
        int reviewCount = building.getReviews().size();
        boolean reviewAuth = reviewWriteAuthority(building.getLocation(),memberId);
        if (building.getLocation() != null && memberId != null) {
            reviewAuth = reviewWriteAuthority(building.getLocation(), memberId);
        }
        boolean isLiked = favoriteService.existsByLocationAndMemberId(building.getLocation(),memberId);

        BuildingsDto buildingDto = new BuildingsDto(
                building.getId(),
                building.getLocation(),
                building.getPosx(),
                building.getPosy(),
                building.getTotalgrade(),
                reviewCount,
                isLiked,
                reviewAuth
        );

        return buildingDto;
    }

    @Cacheable(value = "buildings", key = "#lat + '-' + #lon + '-' + #distance + '-' + #memberId")
    public List<BuildingsDto> GetBuildingsForMap (double latitude, double longitude, double distance, String memberId) {
        List<Building> buildings = new ArrayList<>();
        double[] boundingBox = getBoundingBox(latitude, longitude, distance);
        buildings.addAll(buildingService.findByPosxBetweenAndPosyBetween(boundingBox[0], boundingBox[2], boundingBox[1], boundingBox[3]));
        List<BuildingsDto> reviewDtos = new ArrayList<>();
        for (Building building : buildings) {
            int reviewCount = building.getReviews().size();
            boolean reviewAuth = reviewWriteAuthority(building.getLocation(),memberId);
            if (building.getLocation() != null && memberId != null) {
                reviewAuth = reviewWriteAuthority(building.getLocation(), memberId);
            }
            boolean isLiked = favoriteService.existsByLocationAndMemberId(building.getLocation(),memberId);
            reviewDtos.add(new BuildingsDto(building.getId(), building.getLocation(), building.getPosx(), building.getPosy(), building.getTotalgrade(),reviewCount, isLiked,reviewAuth));
        }
        return reviewDtos;
    }


    public List<Review> findByBuilding(Long buildingId){
        return reviewRepository.findByBuildingId(buildingId);
    }

    public boolean existsByMemberIdAndLocation(String memberId, String location){
        return reviewRepository.existsByMemberIdAndLocation(memberId, location);
    }

    /** 인증 location 확인 **/
    public Boolean reviewWriteAuthority(String location, String memberId) {
        Member member = memberService.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없음.."));
        if (member.getValid() == Valid.UNCERTIFIED)
            return false;
        else if (member.getLocation()!= location)
            return false;
        else if (existsByMemberIdAndLocation(memberId, location))
            return false;
        else
            return true;
    }
}
