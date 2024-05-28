package homes.has.dto;

import lombok.Getter;


@Getter
public class BuildingsDto{
    private Long id;
    private String location;
    private Double posx;
    private Double posy;
    private Double totalgrade = 0.0;
    private Integer reviewCount;
    private Boolean isLiked;
    private Boolean reviewAuth;

    public BuildingsDto(Long id, String name, double posx, double posy, Double totalGrade, int reviewCount, boolean isLiked, boolean reviewAuth) {
        this.id = id;
        this.location = name;
        this.posx = posx;
        this.posy = posy;
        this.totalgrade = totalGrade;
        this.reviewCount = reviewCount;
        this.isLiked = isLiked;
        this.reviewAuth = reviewAuth;
    }
}
