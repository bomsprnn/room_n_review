package homes.has.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Embeddable
@NoArgsConstructor

public class ReviewGrade {
    private double lessor;
    private double quality;
    private double area;
    private double noise;

    public ReviewGrade (double lessor, double quality, double area, double noise) {
        this.lessor= lessor;
        this.quality=quality;
        this.area =area;
        this.noise=noise;
    }
}