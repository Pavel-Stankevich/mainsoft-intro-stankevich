package by.mainsoft.intro.stankevich.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Run {
    private Long id;
    @JsonIgnore
    private User user;
    private BigDecimal distance;
    private LocalDateTime startTime;
    private LocalTime time;
}
