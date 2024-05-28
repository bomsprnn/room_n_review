package homes.has.service;

import homes.has.dto.PostQueryDto;
import homes.has.enums.Category;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.dto.PostDto;
import homes.has.repository.PostQueryRepository;
import homes.has.repository.PostRepository;
import homes.has.repository.PostSearchCond;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostQueryRepository queryRepository;
    @Autowired
    PostService postService;
    @Autowired
    MemberService memberService;

    @Test
    void 게시글_검색() {
        Member member = new Member();
        memberService.save(member);
        Post post1 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post2 = createPost(member,null, "akima", "aaaa");
        Post post3 = createPost(member, Category.GENERAL, "aaaa", "aaaa");

        PostSearchCond kim = new PostSearchCond("kim",Category.GENERAL);
        PostSearchCond kim2 = new PostSearchCond("kim");

        List<PostQueryDto> findPost = postService.findByWord(kim);
        List<PostQueryDto> findPost2 = postService.findByWord(kim2);

//        DTO 변경 에 따른 테스트 수정 필

//        assertThat(findPost).doesNotContain(post2,post3);
//
//        assertThat(findPost2).contains(post1,post2);
//        assertThat(findPost2).doesNotContain(post3);
    }


    @Test
    void commHomeTest(){
        Member member = new Member();
        memberService.save(member);
        Post post1 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post2 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post3 = createPost(member, Category.GENERAL, "akima", "bkimb");
        Post post4 = createPost(member, Category.GENERAL, "akima", "bkimb");

        Post post5 = createPost(member, Category.MARKET, "aa", "aa");
        Post post6 = createPost(member, Category.MARKET, "bb", "bb");
        Post post7 = createPost(member, Category.MARKET, "cc", "cc");
        Post post8 = createPost(member, Category.MARKET, "dd", "dd");

        Post post9 = createPost(member, Category.TIPS, "akima", "bkimb");
        Post post10 = createPost(member, Category.TIPS, "akima", "bkimb");
        Post post11 = createPost(member, Category.TIPS, "akima", "bkimb");
        Post post12 = createPost(member, Category.TIPS, "akima", "bkimb");

        Map<Category,List<PostDto>> map = postService.communityMainPost();


        List<PostDto> marketPostDtos = map.get(Category.MARKET);
        List<PostDto> generalPostDtos = map.get(Category.GENERAL);
        List<PostDto> tipsPostDtos = map.get(Category.TIPS);


        assertThat(marketPostDtos.size()).isEqualTo(3);
        assertThat(generalPostDtos.size()).isEqualTo(3);
        assertThat(tipsPostDtos.size()).isEqualTo(3);
//        최신순으로 가져옴 확인
        assertThat(marketPostDtos.get(0).getId()).isEqualTo(post8.getId());
        assertThat(marketPostDtos.get(1).getId()).isEqualTo(post7.getId());
        assertThat(marketPostDtos.get(2).getId()).isEqualTo(post6.getId());

    }



    @Test
    void 업데이트_테스트(){
        Member member = new Member();
        memberService.save(member);
        Post post1 = createPost(member, Category.GENERAL, "akima", "bkimb");
        PostDto postDto = PostDto.builder()
                .body("수정한 게시글 내용")
                .title("게시글 수정 수정...")
                .build();
//        postService.update(post1.getId(), postDto);

//        assertThat(post1.getTitle()).isEqualTo("게시글 수정 수정...");
//        assertThat(post1.getBody()).isEqualTo("수정한 게시글 내용");

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



}