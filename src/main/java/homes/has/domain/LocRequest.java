package homes.has.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import homes.has.domain.timestamp.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="Request_location")
public class LocRequest extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "imageFile_id")
    private ImageFile imageFile;
    @Builder
    public LocRequest(String location, ImageFile imageFile, Member member){
        this.imageFile=imageFile;
        this.location=location;
        this.member=member;
    }




}
