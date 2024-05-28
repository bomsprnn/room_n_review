package homes.has.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Builder
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    private String location;

   // @ElementCollection
   // private List<String> imageURL= new ArrayList<>();

    @Embedded
    private ReviewBody body;

    @Embedded
    private ReviewGrade grade;

    //public void addImageUrl(String url) {imageURL.add(url);}

    @JsonIgnore
    @OneToMany(mappedBy = "review")
    private List<ReviewImageFile> reviewImageFiles = new ArrayList<>();


}