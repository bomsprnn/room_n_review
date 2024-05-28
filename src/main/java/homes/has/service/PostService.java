package homes.has.service;


import homes.has.domain.ImageFile;
import homes.has.domain.PostImageFile;
import homes.has.dto.PostQueryDto;
import homes.has.enums.Category;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.dto.PostDto;
import homes.has.repository.PostQueryRepository;
import homes.has.repository.PostRepository;
import homes.has.repository.PostSearchCond;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository queryRepository;


    public Long save(Post post) {
        return postRepository.save(post).getId();
    }

    public Optional<Post>  findById(Long postId){
        return postRepository.findById(postId);
    }

//    게시판 검색 기능
    public List<PostQueryDto> findByWord(PostSearchCond cond){
        List<PostQueryDto> posts = queryRepository.findPostByTitleAndBodyInCategoryContaining(cond);
        for (PostQueryDto post : posts) {
            System.out.println(post.getMemberId());
        }
        return posts;
    }

    public void increaseLikes(Long postId){
        postRepository.increaseLikes(postId);
    }

    public void increaseComments(Long postId){
        postRepository.increaseComments(postId);
    }

    public void decreaseLikes(Long postId){
        postRepository.decreaseLikes(postId);
    }

    public void decreaseComments(Long postId){
        postRepository.decreaseComments(postId);
    }

    //    main에 노출할 최신글 3개, map으로 return
    public Map<Category,List<PostDto>> communityMainPost(){
        Map<Category,List<PostDto>> map = new HashMap<>();

        for (Category cat : Category.values()) {
            List<Post> catPost = postRepository.findTop3ByCategoryOrderByCreatedAtDesc(cat);
            List<PostDto> postDtos= new ArrayList<>();
            for (Post post : catPost) {
                Member member = post.getMember();
                String authorName = member.getLocation() + "_"+ member.getNickName().charAt(0);
                PostDto postDto = PostDto.builder()
                        .id(post.getId())
                        .category(post.getCategory())
                        .commentCount(post.getCommentCount())
                        .createdAt(post.getCreatedAt())
                        .likes(post.getLikes())
                        .title(post.getTitle())
                        .body(post.getBody())
                        .modifiedAt(post.getModifiedAt())
                        .authorName(authorName)
                        .memberId(member.getId())
                        .build();
                postDtos.add(postDto);
            }
            map.put(cat,postDtos);
        }

        return map;
    }

    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }

    public void update(Long postId, PostDto postDto, List<PostImageFile> postImageFiles){
        Post post = postRepository.findById(postId).get();

        post.update(postDto.getTitle(), postDto.getBody(), postImageFiles);
    }

    public List<Post> findByCategory(Category category){
        return postRepository.findByCategory(category);
    }

    public Post popularPost(Category category, LocalDateTime start, LocalDateTime end){
        return postRepository.findFirstByCategoryAndCreatedAtBetweenOrderByLikesDesc(category,start,end);
    }


}
