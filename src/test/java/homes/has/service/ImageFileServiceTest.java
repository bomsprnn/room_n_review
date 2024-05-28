package homes.has.service;

import homes.has.domain.ImageFile;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.domain.PostImageFile;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@Transactional
class ImageFileServiceTest {
    @Autowired
    ImageFileService imageFileService;
    @Autowired
    PostService postService;
    @Autowired
    PostImageFileService postImageFileService;





//    @Test
//    void post와_imagefile_entity_관계_test(){
//        Member member = Member.builder().nickName("abc").location("금호동 3길").build();
//        List<PostImageFile> a = new ArrayList<>();
//        Post post = Post.builder()
//                .member(member)
//                .body("abc")
//                .title("ddd")
//                .postImageFiles(a)
//                .build();
//
//
//        ImageFile imageFile = new ImageFile(1L, "orgName", "savName", "path", null, null, null);
//        PostImageFile postImageFile = new PostImageFile(post, imageFile);
//        post.getPostImageFiles().add(postImageFile);
//        Long saved = postService.save(post);
//
//        Post post1 = postService.findById(saved).get();
//        log.info("post1= {}",post1);
//    }
}