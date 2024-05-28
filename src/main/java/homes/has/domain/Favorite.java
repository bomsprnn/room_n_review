package homes.has.domain;


import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@NoArgsConstructor
public class Favorite extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String location;
    private double posx;
    private double posy;

    @Builder
    public Favorite(String location, Member member, double posx, double posy) {
        this.location = location;
        this.member = member;
        this.posx = posx;
        this.posy = posy;
    }
}
