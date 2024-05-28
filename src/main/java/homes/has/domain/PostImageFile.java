package homes.has.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "ImageFile_id")
    private ImageFile imageFile;

    public PostImageFile(Post post, ImageFile imageFile) {
        this.post=post;
        this.imageFile= imageFile;
    }
}
