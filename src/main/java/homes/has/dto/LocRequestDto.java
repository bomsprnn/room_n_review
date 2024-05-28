package homes.has.dto;


import homes.has.domain.ImageFile;
import homes.has.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class LocRequestDto {
    private Long id;
    private String name;
    private String nickname;
    private String location;
    private ResponseEntity<byte[]> image;
    private LocalDateTime createdAt;

    @Builder
    public LocRequestDto(Long id,String name,String nickname  , String location, ResponseEntity<byte[]> image, LocalDateTime createdAt){
        this.name= name;
        this.nickname = nickname;
        this.location = location;
        this.image= image;
        this.id= id;
        this.createdAt= createdAt;

    }
}
