package homes.has.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Embeddable
@NoArgsConstructor
public class ReviewBody {
    private String advantage;
    private String weakness;
    private String etc;

    public ReviewBody (String advantage,String weakness, String etc) {
        this.advantage=advantage;
        this.weakness=weakness;
        this.etc=etc;
    }

}
