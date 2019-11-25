package by.mainsoft.intro.stankevich.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "races")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, precision = 18, scale = 3)
    @Digits(integer = 18, fraction = 3)
    @NonNull
    private BigDecimal distance;
    @Column(nullable = false)
    @NonNull
    private LocalDateTime startTime;
    @Column(nullable = false)
    @NonNull
    private LocalTime duration;
}
