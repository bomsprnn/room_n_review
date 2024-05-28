package homes.has.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import homes.has.domain.timestamp.BaseEntity;
import homes.has.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int likes;

    private int commentCount;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<PostImageFile> postImageFiles = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<LikePosts> likePosts = new ArrayList<>();
    @Builder
    public Post(Member member, String title, String body, Category category, List<PostImageFile> postImageFiles){
        this.member = member;
        this.title= title;
        this.body= body;
        this.category = category;
        this.postImageFiles= postImageFiles;

        this.likes=0;
        this.commentCount = 0;
    }

    public void update(String title, String body, List<PostImageFile> postImageFiles){
        this.title= title;
        this.body = body;
        for (PostImageFile postImageFile : postImageFiles) {
            this.postImageFiles.add(postImageFile);
        }
    }



}
