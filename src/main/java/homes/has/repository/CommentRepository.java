package homes.has.repository;

import homes.has.domain.Comment;
import homes.has.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {

//    좋아요 수 증가
    @Modifying(clearAutomatically = true)
    @Query("update Comment c set c.likes = c.likes+1 where c.id = :id")
    public void increaseLikes(@Param("id")Long id);


    @Modifying(clearAutomatically = true)
    @Query("update Comment c set c.likes = c.likes-1 where c.id = :id")
    public void decreaseLikes(@Param("id")Long id);


    @Query("select c from Comment c where c.post.id = :postId")
    public List<Comment> findByPostId(@Param("postId") Long postId);


    void deleteById(Long commentId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.member m WHERE m.id = :memberId")
    public List<Comment> memberComment(@Param("memberId") String memberId);

    public void deleteByPostId(Long postId);

}
