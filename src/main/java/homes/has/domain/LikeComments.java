package homes.has.domain;

import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LikeComments {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public LikeComments(Comment comment, Member member){
        this.comment = comment;
        this.member=member;
    }

}
