package homes.has.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReviewImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "ImageFile_id")
    private ImageFile imageFile;

    public ReviewImageFile(Review review, ImageFile imageFile) {
        this.review=review;
        this.imageFile= imageFile;
    }

}
