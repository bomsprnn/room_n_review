package homes.has.domain;


import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LikePosts {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public LikePosts(Post post, Member member){
        this.post= post;
        this.member = member;
    }
}
