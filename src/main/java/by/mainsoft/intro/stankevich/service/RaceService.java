package by.mainsoft.intro.stankevich.service;

import by.mainsoft.intro.stankevich.model.Race;
import by.mainsoft.intro.stankevich.model.RaceReport;
import by.mainsoft.intro.stankevich.model.User;

import java.util.List;

public interface RaceService {

    void save(final User user, final Race race);

    List<Race> findAllUserRaces(final User user);

    Race findUserRaceById(final User user, final Long id);

    void delete(final User user, final Long id);

    List<RaceReport> getWeekReports(final User user);
}
