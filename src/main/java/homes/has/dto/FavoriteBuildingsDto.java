package homes.has.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FavoriteBuildingsDto{
    private Long id;
    private String location;
    private double posx;
    private double posy;
    private Double totalGrade = 0.0;
    private Integer reviewCount =0;
    private LocalDateTime createdAt;
    private Boolean isLiked;

    public FavoriteBuildingsDto(Long id, String name, double posx, double posy, Double totalGrade, int reviewCount, boolean isLiked, LocalDateTime createdAt) {
        this.id = id;
        this.location = name;
        this.posx= posx;
        this.posy=posy;
        this.totalGrade = totalGrade;
        this.reviewCount = reviewCount;
        this.isLiked = isLiked;
        this.createdAt= createdAt;
    }
}

