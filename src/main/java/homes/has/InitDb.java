package homes.has;

import homes.has.domain.*;
import homes.has.enums.Category;
import homes.has.enums.Valid;
import homes.has.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class InitDb {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private LikePostService likePostService;

    @Autowired
    private FavoriteService favoriteService;
    @PostConstruct
    public void init() throws IOException {
        Member member1 = Member.builder().name("김철수")
                .valid(Valid.CERTIFIED)
                .location("가좌로 3길")
                .nickName("oneroom")
                .id("abc")
                .build();
        Member member2 = Member.builder().name("aaa")
                .valid(Valid.CERTIFIED)
                .location("가좌로 2길")
                .nickName("helloWorld")
                .id("def")
                .build();
        Member member3 = Member.builder().name("신지현")
                .valid(Valid.CERTIFIED)
                .location("가좌로 1길")
                .nickName("lorem")
                .id("ghi")
                .build();
        memberService.save(member1);
        memberService.save(member2);
        memberService.save(member3);

        createPost(member1, Category.GENERAL, "일단 나는 아님", "킹망고설빙 먹어본사람");
        createPost(member2, Category.GENERAL, "기사님 선곡 굿~", "방금 택시 탔는데");
        Post post2 = createPost(member3, Category.GENERAL, "범죄도시3? 트랜스포머? 아님 한 번 틀어서 포켓몬스터극장판", "영화 뭐보지?");
        Post post1 = createPost(member1, Category.GENERAL, "89점. 음색은 좋은데 기교가 너무 심함", "지금 노래하는놈 누구냐?");

        createPost(member2, Category.MARKET, "여름에 모기 태우려고요", "베이스앰프 삽니다");
        createPost(member3, Category.MARKET, "오늘 이사하는데 너무 많아서...", "냉동만두 가져가실분");
        createPost(member1, Category.MARKET, "50 쿨거", "그래픽카드 팝니다 지포스 RTX 3080 D6X 10GB");
        createPost(member2, Category.MARKET, "10만원입니다 거의 새 거", "러셀홉스 맷돌방식 그라인더 팔아용 네고가능");
        createPost(member2, Category.MARKET, "롯데시네마 예매권~~ 7천원", "영화표 팔아용");


        createPost(member3, Category.TIPS, "과잉진료 바가지 없는...", "경대 주변에 피부과 괜찮은 데 있나요?");
        createPost(member1, Category.TIPS, "잘보고따라해...", "자취친구들아 누나가 레시피 하나 공유한다");
        createPost(member2, Category.TIPS, "풀번 달라고 해도 되나? 실례인가", "뮤지컬 양도 어떻게 받나요");
        createPost(member3, Category.TIPS, "로티세리에 베이컨 추가+파마산+슈레드+스윗어니언렌치후추", "서브웨이조합추천. 잡솨봐");


//      이미지 출력 테스트용
//        createPost(member3, Category.GENERAL, "안녕", "반가워");

        favoriteService.CreateFavorite("가좌로 3길",member1.getId(),37.3,125.2);
        favoriteService.CreateFavorite("진주대로 500번길",member1.getId(),37.1,128.2);
        favoriteService.CreateFavorite("가좌로 3길",member2.getId(),37.3,125.2);
        favoriteService.CreateFavorite("진주대로 550번길",member1.getId(),37.1,128.2);
        favoriteService.CreateFavorite("진주대로 550번길",member2.getId(),37.2,125.2);

        commentService.save(Comment.builder().member(member3).post(post1).body("ㄹㅇㅋㅋ").build());
        commentService.save(Comment.builder().member(member1).post(post1).body("맞아맞아").build());
        commentService.save(Comment.builder().member(member2).post(post1).body("나도 그렇게 생각해").build());

//        likePostService.save(new LikePosts(post1,member1));
//        likePostService.save(new LikePosts(post1,member2));
//        likePostService.save(new LikePosts(post1,member3));

        for (int i = 0; i < 5; i++) {
            postService.increaseLikes(post1.getId());
        }
        for (int i = 0; i < 7; i++) {
            postService.increaseLikes(post2.getId());
        }

        String location = "진주대로 500번길";
        ReviewGrade grade = new ReviewGrade(5, 3, 2, 4);
        ReviewBody body = new ReviewBody("볕이 잘 들고... 뷰  가 조타...ㅎㅎ", "건물 옆에 커다란 느티나무가 있는데요.. 그래서인지 벌레가 너무 많이 꼬임...", "벽지가  넘무 파래여 집에 있으면 창백해보임니다");

        String location2 = "진주대로 550번길";
        ReviewGrade grade2 = new ReviewGrade(4, 4, 1, 4);
        ReviewBody body2 = new ReviewBody("주인장이 뿌링클을 사주심니다 ...냠냠..", "학교랑 넘나리 먹어요  ", "몰...루");

        ReviewGrade grade3 = new ReviewGrade(4, 4, 1, 4);
        ReviewBody body3 = new ReviewBody("테테테테테테테스트", "....", "테스트 임니다아 500번길이요오");

        // when
        reviewService.CreateReview(member1.getId(),location, grade, body,37.1,128.2, null);
        reviewService.CreateReview(member2.getId(),location2, grade2, body2,37.2,125.2, null);
        reviewService.CreateReview(member2.getId(),location2, grade3, body3,37.2,125.2, null);
        reviewService.CreateReview(member1.getId(),location, grade, body,37.1,128.2, null);

//        reviewService.CreateReview(member1.getId(),"경남 진주시 가좌길 48번길 9-1",grade,body,35.1583004240859,128.106283133998, null);
//        reviewService.CreateReview(member1.getId(),"경남 진주시 가좌길 48번길 9-1",grade2,body2,35.1583004240859,128.106283133998, null);
//        reviewService.CreateReview(member1.getId(),"경남 진주시 가좌길 48번길 9-1",grade3,body3,35.1583004240859,128.106283133998, null);

    }

    private Post createPost(Member member , Category category, String body, String title) {
        Post post = Post.builder()
                .member(member)
                .title(title)
                .body(body)
                .category(category)
                .build();
        Long saved = postService.save(post);
        return post;
    }
    private Post createPost(Member member , Category category, String body, String title, MultipartFile file) {
        Post post = Post.builder()
                .member(member)
                .title(title)
                .body(body)
                .category(category)
                .build();
        Long saved = postService.save(post);
        return post;
    }

}
