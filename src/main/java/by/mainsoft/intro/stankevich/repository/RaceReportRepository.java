package by.mainsoft.intro.stankevich.repository;

import by.mainsoft.intro.stankevich.model.RaceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RaceReportRepository extends JpaRepository<RaceReport, Integer> {

    @Query(value = "SELECT " +
            "    cast(extract('week' from start_time) AS int) AS week_number, " +
            "    cast(date_trunc('week', start_time) AS date) AS from_date, " +
            "    cast(date_trunc('week', start_time) + cast('6 days' AS interval) AS date) AS to_date, " +
            "    cast(avg(" +
            "           distance /" +
            "           cast((" +
            "                   (extract(HOUR FROM duration) * 60 * 60) + " +
            "                   (extract(MINUTE FROM duration) * 60) + " +
            "                   extract(SECOND FROM duration)" +
            "               ) / (60 * 60) AS numeric)" +
            "           ) AS numeric(18, 3)) avg_speed, " +
            "    cast(avg(duration) AS time) AS avg_duration, " +
            "    cast(sum(distance) AS numeric(18, 3)) AS total_distance " +
            "FROM public.races " +
            "WHERE user_id = :user_id " +
            "GROUP BY extract('week' from start_time), date_trunc('week', start_time) " +
            "ORDER BY 1", nativeQuery = true)
    List<RaceReport> getReportsByUserId(final @Param("user_id") Long userId);
}
