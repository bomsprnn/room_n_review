package homes.has.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import homes.has.domain.timestamp.BaseEntity;
import homes.has.enums.Valid;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@Entity
@NoArgsConstructor()
public class Member extends BaseEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Valid valid;

    private String location;

    private String name;

    private String nickName;


    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy= "member")
    private List<Favorite> favorites = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy= "member")
    private List<LocRequest> requests = new ArrayList<>();

    public void changeValid(Valid valid){
        this.valid = valid;
    }

    public void changeLocation(String location){
        this.location = location;
    }

    @Builder
    public Member(String id, Valid valid, String location, String name, String nickName){
        this.valid = valid;
        this.location = location;
        this.name=name;
        this.nickName=nickName;
        this.id=id;
    }
}
