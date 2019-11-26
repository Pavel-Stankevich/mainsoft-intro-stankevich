package by.mainsoft.intro.stankevich.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceReport {

    @Id
    @Column(name = "week_number")
    private Integer weekNumber;
    @Column(name = "from_date")
    private LocalDate fromDate;
    @Column(name = "to_date")
    private LocalDate toDate;
    @Column(name = "avg_speed")
    private BigDecimal avgSpeed;
    @Column(name = "avg_duration")
    private LocalTime avgDuration;
    @Column(name = "total_distance")
    private BigDecimal totalDistance;
}
