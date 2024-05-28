package homes.has.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Building{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location; //도로명주소

    private double posx;
    private double posy;

    /*@Embedded
     private ReviewGrade reviewGrade;*/
    private Double totalgrade = 0.0;

    //@JsonIgnore
    //@OneToMany(mappedBy = "building")
    //private List<Favorite> favorites = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "building",cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    public Building(String location, double posx, double posy) {
        this.location = location;
        this.posx = posx;
        this.posy = posy;
    }
    public void addReview(Review review){
        this.reviews.add(review);
    }
}
