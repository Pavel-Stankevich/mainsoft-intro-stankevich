package by.mainsoft.intro.stankevich.repository;

import by.mainsoft.intro.stankevich.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<Race, Long> {

    Optional<Race> findByUserIdAndId(final Long userId, final Long id);

    List<Race> findAllByUserId(final Long userId);

    void deleteByUserIdAndId(final Long userId, final Long id);
}
