package by.mainsoft.intro.stankevich.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@SqlResultSetMapping(
        name = "raceReportResultSerMapping",
        entities = {
                @EntityResult(entityClass = RaceReport.class, fields = {
                        @FieldResult(name = "weekNumber", column = "week_number"),
                        @FieldResult(name = "fromDate", column = "from_date"),
                        @FieldResult(name = "toDate", column = "to_date"),
                        @FieldResult(name = "avgSpeed", column = "avg_speed"),
                        @FieldResult(name = "avgDuration", column = "avg_duration"),
                        @FieldResult(name = "totalDistance", column = "total_distance")
                })
        },
        classes = {
                @ConstructorResult(
                        targetClass = RaceReport.class,
                        columns = {
                                @ColumnResult(name = "week_number", type = Integer.class),
                                @ColumnResult(name = "from_date", type = LocalDate.class),
                                @ColumnResult(name = "to_date", type = LocalDate.class),
                                @ColumnResult(name = "avg_speed", type = BigDecimal.class),
                                @ColumnResult(name = "avg_duration", type = LocalTime.class),
                                @ColumnResult(name = "total_distance", type = BigDecimal.class)
                        }
                )
        }
)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceReport {

    @Id
    private Integer weekNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal avgSpeed;
    private LocalTime avgDuration;
    private BigDecimal totalDistance;
}
