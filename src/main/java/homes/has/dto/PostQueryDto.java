package homes.has.dto;

import homes.has.domain.Member;
import homes.has.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class PostQueryDto {

    private Long id;
    private String title;
    private String body;
    private Category category;
    private int likes;
    private int commentCount;
    private String memberId;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostQueryDto(Long id, String title, String body, Category category ,
                        int likes, int commentCount, String memberId ,
                        LocalDateTime createdAt, LocalDateTime modifiedAt,
                        String location, String nickname){
        this.id=id;
        this.memberId = memberId;
        this.title= title;
        this.body= body;
        this.category = category;
        this.likes=likes;
        this.commentCount = commentCount;
        this.createdAt= createdAt;
        this.modifiedAt = modifiedAt;
        this.authorName= location+"_"+ nickname.charAt(0);
    }
}
