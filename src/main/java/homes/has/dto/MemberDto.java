package homes.has.dto;


import homes.has.enums.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class MemberDto {

    private String id;
    private Valid valid;
    private String location;
    private String name;
    private String nickName;

    @Builder
    public MemberDto(String id, Valid valid, String location, String name, String accessToken, String provideId, String nickName){
        this.id =id;
        this.valid=valid;
        this.location=location;
        this.name= name;
        this.nickName=nickName;
    }
}
