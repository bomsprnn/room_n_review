package homes.has.repository;

import homes.has.enums.Category;
import homes.has.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long>{


//    auditing 반영 안하기 위해서 사용
//    좋아요 수 증가
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.likes = p.likes+1 where p.id = :id")
    public void increaseLikes(@Param("id")Long id);

//    댓글 수 증가
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.commentCount = p.commentCount+1 where p.id = :id")
    public void increaseComments(@Param("id")Long id);

    //    좋아요 수 감소

    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.likes = p.likes-1 where p.id = :id")
    public void decreaseLikes(@Param("id")Long id);

    //    댓글 수 감소
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.commentCount = p.commentCount-1 where p.id = :id")
    public void decreaseComments(@Param("id")Long id);



    //  카테고리별 최신글 3개
    public List<Post> findTop3ByCategoryOrderByCreatedAtDesc(Category category);


    public void deleteById(Long postId);

    public List<Post> findByCategory(Category category);


    @Query("SELECT p FROM Post p JOIN FETCH p.member m WHERE m.id = :memberId")
    public List<Post> memberPost(@Param("memberId") String memberId);


//    @Query("SELECT p FROM Post p WHERE p.category = :category AND p.createdAt >=  CURRENT_TIMESTAMP - INTERVAL 2 DAY ORDER BY p.likes DESC")
//    Post popularPost (@Param("category") Category category );

    Post findFirstByCategoryAndCreatedAtBetweenOrderByLikesDesc(Category category, LocalDateTime start, LocalDateTime end);
}
