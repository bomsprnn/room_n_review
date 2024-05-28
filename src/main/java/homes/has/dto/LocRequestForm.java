package homes.has.dto;


import homes.has.domain.ImageFile;
import homes.has.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
public class LocRequestForm {

    private String memberId;
    private String location;
    private LocalDateTime createdAt;
    private MultipartFile file;

    @Builder
    public LocRequestForm(String memberId, String location, MultipartFile file, LocalDateTime createdAt){
        this.memberId=memberId;
        this.location = location;
        this.file= file;
        this.createdAt= createdAt;
    }
}
