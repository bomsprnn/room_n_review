package homes.has.dto;


import homes.has.domain.PostImageFile;
import homes.has.enums.Category;
import homes.has.domain.ImageFile;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    private String body;

    private Category category;

    private int likes;
    private List<MultipartFile> files;
    private List<ResponseEntity<byte[]>> images;
    private int commentCount;
    private String memberId;
    private List<CommentDto> comments;
    private String authorName;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    @Builder
    public PostDto(String memberId, Category category, Long id, String title, String body, int likes,
                   int commentCount, List<CommentDto> comments, LocalDateTime createdAt,
                   LocalDateTime modifiedAt, String authorName, List<ResponseEntity<byte[]>> images) {
        this.memberId = memberId;
        this.body = body;
        this.category=category;
        this.id =id;
        this.title = title;
        this.likes = likes;
        this.commentCount = commentCount;
        this.comments = comments;
        this.createdAt = createdAt;
        this.modifiedAt=modifiedAt;
        this.authorName=authorName;
        this.images = images;
    }



}
