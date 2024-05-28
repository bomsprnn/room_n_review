package homes.has.domain.timestamp;

import homes.has.enums.Category;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
class BaseEntityTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    EntityManager em;


    @Test
    void timestampTest() throws InterruptedException {
        Member member = new Member();
        memberService.save(member);
        Post post = Post.builder()
                .member(member)
                .title("abc")
                .body("abcde")
                .category(Category.GENERAL)
                .build();
        Long postId = postService.save(post);

        Thread.sleep(1000);

//        post.setBody("asdsa");
        Post findPost = postService.findById(postId).get();
        em.flush();
        em.clear();

        LocalDateTime createdAt = findPost.getCreatedAt();
        LocalDateTime modifiedAt = findPost.getModifiedAt();
        log.info("createdAt={}" ,createdAt);
        log.info("modifiedAt={}" ,modifiedAt);

        postService.increaseComments(postId);
        Thread.sleep(1000);

        Post findPost2 = postService.findById(postId).get();
        LocalDateTime createdAt2 = findPost2.getCreatedAt();
        LocalDateTime modifiedAt2 = findPost2.getModifiedAt();
        log.info("createdAt={}" ,createdAt);
        log.info("modifiedAt2={}" ,modifiedAt2);


        assertThat(createdAt).isEqualTo(createdAt2);
        assertThat(createdAt).isNotEqualTo(modifiedAt);
        assertThat(modifiedAt).isEqualTo(modifiedAt2);




    }
}