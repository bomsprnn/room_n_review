package homes.has.service;


import homes.has.domain.LikePosts;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.repository.LikePostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class LikePostService {
    private final LikePostsRepository likePostsRepository;

//    public List<Post> findByMemberId(Long memberId){
//        return likePostsRepository.findByMemberId(memberId);
//    }

    public Long save(LikePosts likePosts) {

        return likePostsRepository.save(likePosts).getId();
    }

    public void delete(Long likePostId){
        likePostsRepository.deleteById(likePostId);
    }
    public void delete(LikePosts likePosts){
        likePostsRepository.delete(likePosts);
    }

    public LikePosts findByPostIdAndMemberId(Long postId, String memberId){
        return likePostsRepository.findByPostIdAndMemberId(postId,memberId);
    }

    public boolean isPostLikedByMember(Post post, Member member) {
        return likePostsRepository.existsByPostAndMember(post, member);
    }

    public List<LikePosts> findByPostId(Long postId){
        return likePostsRepository.findByPostId(postId);
    }
}
