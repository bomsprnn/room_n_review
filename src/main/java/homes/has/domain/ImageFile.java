package homes.has.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalFileName;  // 파일 원본명
    private String savedName;
    private String path;  // 파일 저장 경로

    @JsonIgnore
    @OneToOne(mappedBy = "imageFile")
    private PostImageFile postImageFile;

    @JsonIgnore
    @OneToOne(mappedBy="imageFile")
    private ReviewImageFile reviewImageFile;

    @JsonIgnore
    @OneToOne(mappedBy = "imageFile")
    private LocRequest locRequest;

    @Builder
    public ImageFile(Long id, String originalFileName, String savedName, String path,
                     ReviewImageFile reviewImageFile, LocRequest locRequest, PostImageFile postImageFile ) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.savedName = savedName;
        this.path =path;
        this.postImageFile = postImageFile;
        this.reviewImageFile= reviewImageFile;
        this.locRequest=locRequest;
    }




}
