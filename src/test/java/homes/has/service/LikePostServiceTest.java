package homes.has.service;

import homes.has.domain.*;
import homes.has.enums.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Transactional
class LikePostServiceTest {

    @Autowired
    LikePostService likePostService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

//    @Test
//    void isliked(){
////        given
//        Member member1 = new Member(Valid.CERTIFIED, "가좌로 3길");
//        Member member2 = new Member(Valid.CERTIFIED, "가좌로 2길");
//        Member member3 = new Member(Valid.CERTIFIED, "가좌로 1길");
//        memberService.save(member1);
//        memberService.save(member2);
//        memberService.save(member3);
//
//        createPost(member1, Category.GENERAL, "akima", "bkimb");
//        createPost(member2, Category.GENERAL, "akima", "bkimb");
//        createPost(member3, Category.GENERAL, "akima", "bkimb");
//        Post post1 = createPost(member1, Category.GENERAL, "akima", "bkimb");
//
//        createPost(member2, Category.MARKET, "aa", "aa");
//        createPost(member3, Category.MARKET, "bb", "bb");
//        createPost(member1, Category.MARKET, "cc", "cc");
//        Post post2 = createPost(member2, Category.MARKET, "dd", "dd");
//
//        createPost(member3, Category.TIPS, "akima", "bkimb");
//        createPost(member1, Category.TIPS, "akima", "bkimb");
//        createPost(member2, Category.TIPS, "akima", "bkimb");
//        createPost(member3, Category.TIPS, "akima", "bkimb");
//
//        commentService.save(Comment.builder().member(member3).post(post1).body("ㄹㅇㅋㅋ").build());
//        commentService.save(Comment.builder().member(member1).post(post1).body("맞아맞아").build());
//        commentService.save(Comment.builder().member(member2).post(post1).body("나도 그렇게 생각해").build());
//
//        Long saved1 = likePostService.save(new LikePosts(post1, member1));
//        Long saved2 = likePostService.save(new LikePosts(post1, member2));
//        Long saved3 = likePostService.save(new LikePosts(post1, member3));
//
//        boolean m1p1 = likePostService.isPostLikedByMember(post1, member1);
//        boolean m2p1 = likePostService.isPostLikedByMember(post1, member2);
//        boolean m1p2 = likePostService.isPostLikedByMember(post2, member1);
//
//
////        then
//        assertThat(m1p1).isTrue();
//        assertThat(m2p1).isTrue();
//        assertThat(m1p2).isFalse();
//    }
//


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

}