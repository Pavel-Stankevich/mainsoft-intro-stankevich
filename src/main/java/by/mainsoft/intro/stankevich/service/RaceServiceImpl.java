package by.mainsoft.intro.stankevich.service;

import by.mainsoft.intro.stankevich.exception.RaceNotFoundException;
import by.mainsoft.intro.stankevich.model.Race;
import by.mainsoft.intro.stankevich.model.RaceReport;
import by.mainsoft.intro.stankevich.model.User;
import by.mainsoft.intro.stankevich.repository.RaceReportRepository;
import by.mainsoft.intro.stankevich.repository.RaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;
    private final RaceReportRepository raceReportRepository;

    @Transactional
    @Override
    public void save(final User user, final Race race) {
        final User user0 = new User();
        user0.setId(user.getId());
        user0.setUsername(user.getUsername());
        user0.setPassword(user.getPassword());
        race.setUser(user0);
        raceRepository.save(race);
    }

    @Transactional
    @Override
    public List<Race> findAllUserRaces(final User user) {
        return raceRepository.findAllByUserId(user.getId());
    }

    @Transactional
    @Override
    public Race findUserRaceById(final User user, final Long id) {
        final Optional<Race> optionalRace = raceRepository.findByUserIdAndId(user.getId(), id);
        if (optionalRace.isPresent()) {
            return optionalRace.get();
        } else {
            throw new RaceNotFoundException(id);
        }
    }

    @Transactional
    @Override
    public void delete(final User user, final Long id) {
        raceRepository.deleteByUserIdAndId(user.getId(), id);
    }

    @Transactional
    @Override
    public List<RaceReport> getWeekReports(final User user) {
        return raceReportRepository.getReportsByUserId(user.getId());
    }
}
