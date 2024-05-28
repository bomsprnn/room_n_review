package homes.has.Controller;

import homes.has.domain.*;
import homes.has.dto.*;
import homes.has.enums.Admin;
import homes.has.enums.FilePath;
import homes.has.enums.Valid;
import homes.has.repository.DetectAdminRepository;
import homes.has.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final LocRequestService locRequestService;
    private final ImageFileService imageFileService;
    private final FavoriteService favoriteService;
    private final DetectAdminRepository detectAdminRepository;

    @PostMapping("/user/sign")
    public void signUp(@RequestBody MemberDto memberDto) throws Exception {
        String id = memberDto.getId();
        if(memberService.isExist(id)) {
            throw new Exception("이미 존재하는 id");
        }
        Member member = Member.builder()
            .valid(Valid.UNCERTIFIED)
            .id(id)
            .name(memberDto.getName())
            .nickName(memberDto.getNickName())
            .build();

        memberService.save(member);

        if (id.equals("tLDScGlDD4fJ5UPnuXlCRbXHjK62")) {
            DetectAdmin detectAdmin = new DetectAdmin();
            detectAdmin.setMember(member);
            detectAdmin.setAdmin(Admin.ADMIN);
            detectAdminRepository.save(detectAdmin);
        }
    }
    
    
    
    @GetMapping("/user/{userId}")
    public MemberDto userInfo(@PathVariable String userId){
        Member member = memberService.findById(userId).get();

        return MemberDto.builder()
                .id(member.getId())
                .valid(member.getValid())
                .location(member.getLocation())
                .name(member.getName())
                .nickName(member.getNickName())
                .build();
    }

    /*
    * 내가 쓴글 api 14번
    * */
    @GetMapping("/user/{memberId}/post")
    public List<PostDto> userPost(@PathVariable String memberId){
        List<Post> posts = memberService.memberPost(memberId);
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            PostDto postDto = createPostDto(memberId, post);
            postDtos.add(postDto);
        }
        postDtos.sort(Comparator.comparing(PostDto::getCreatedAt).reversed());

        return postDtos;
    }

    /*
     *  api 명세 15, 내가 쓴 댓글
    * */
    @GetMapping("/user/{memberId}/comment")
    public List<PostDto> userComment(@PathVariable String memberId){
        List<Comment> comments = memberService.memberComment(memberId);
        List<Post> posts= new ArrayList<>();
        List<PostDto> postDtos = new ArrayList<>();
        for (Comment comment : comments) {
            Long postId = comment.getPost().getId();
            Post post = postService.findById(postId).get();
            posts.add(post);
        }

        for (Post post : posts) {
            PostDto postDto = createPostDto(memberId, post);
            postDtos.add(postDto);
        }
        postDtos.sort(Comparator.comparing(PostDto::getCreatedAt).reversed());
        return postDtos;
    }


    /*
    *  api 명세 11, 주소지 인증
    * */
    @PostMapping("/user/authorization/write")
    public void writeLocRequest(@RequestPart LocRequestForm locRequestForm, @RequestPart MultipartFile file) throws IOException {
        String memberId = locRequestForm.getMemberId();
        Member member = memberService.findById(memberId).get();

        ImageFile imageFile = null;
        if (file!= null) {
            imageFile = imageFileService.saveFile(file, FilePath.LOCREQUEST);
        }

        LocRequest locRequest = LocRequest.builder()
                .location(locRequestForm.getLocation())
                .member(member)
                .imageFile(imageFile)
                .build();


        locRequestService.save(locRequest);
        if(member.getValid()== Valid.UNCERTIFIED)
            memberService.changeValid(member, Valid.ONGOING);
    }


    @Getter
    private static class MemberIds{
        private String memberId;
    }
    /*
    *api 명세 12, 주소지 인증 취소
    * */
    @DeleteMapping("/user/authorization")
    public void deleteLocRequest(@RequestBody MemberIds memberIds){
        String memberId = memberIds.getMemberId();
        Member member = memberService.findById(memberId).get();
        LocRequest locRequest = locRequestService.findByMemberId(memberId);
        ImageFile imageFile = locRequest.getImageFile();
        locRequestService.delete(locRequest);
        imageFileService.delete(imageFile);
        if(member.getValid()==Valid.ONGOING)
            memberService.changeValid(member, Valid.UNCERTIFIED);
    }
    /*
    * api 명세 13, 내가쓴 리뷰
    * */
    @GetMapping("/user/{memberId}/review")
    public List<ReviewDto> userReview(@PathVariable String memberId){
        List<Review> reviews = memberService.memberReview(memberId);
        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDto reviewDto = ReviewDto.builder()
                .grade(review.getGrade())
                .memberId(review.getMember().getId())
                .body(review.getBody())
                .buildingId(review.getBuilding().getId())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .id(review.getId())
                .location(review.getLocation())
                .build();
            reviewDtos.add(reviewDto);
        }
        reviewDtos.sort(Comparator.comparing(ReviewDto::getCreatedAt).reversed());

        return reviewDtos;
    }

    /**
     * 관심 건물 리스트 (API no.19)
     **/
    @GetMapping("/user/{memberId}/favorite")
    public List<FavoriteBuildingsDto> getFavoriteBuildings(@PathVariable("memberId") String memberId) {
        List<FavoriteBuildingsDto> favoriteBuildingsDtos = favoriteService.GetFavoriteBuildings(memberId);
        favoriteBuildingsDtos.sort(Comparator.comparing(FavoriteBuildingsDto::getCreatedAt).reversed());
        return favoriteBuildingsDtos;
    }

    private static PostDto createPostDto(String memberId, Post post) {
        Member member = post.getMember();
        String authorName = member.getLocation() + "_"+ member.getNickName().charAt(0);
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .commentCount(post.getCommentCount())
                .authorName(authorName)
                .memberId(memberId)
                .likes(post.getLikes())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
        return postDto;
    }


}
