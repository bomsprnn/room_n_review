package homes.has.repository;

import homes.has.domain.Comment;
import homes.has.domain.LikePosts;
import homes.has.domain.Member;
import homes.has.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LikePostsRepository extends JpaRepository<LikePosts,Long> {


//    @Query("select lp from LikePosts lp where lp.member.id = :memberId")
//    public List<LikePosts> findByMemberId(@Param("memberId") Long memberId);


    public boolean existsByPostAndMember(Post post, Member member);

    @Query("SELECT lp FROM LikePosts lp WHERE lp.post.id = :postId AND lp.member.id = :memberId")
    public LikePosts findByPostIdAndMemberId(@Param("postId") Long postId, @Param("memberId") String memberId);


    public List<LikePosts> findByPostId(Long postId);


}
