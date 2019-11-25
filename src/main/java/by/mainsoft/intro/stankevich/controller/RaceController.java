package by.mainsoft.intro.stankevich.controller;

import by.mainsoft.intro.stankevich.model.Race;
import by.mainsoft.intro.stankevich.model.RaceReport;
import by.mainsoft.intro.stankevich.security.AuthUser;
import by.mainsoft.intro.stankevich.service.RaceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/races/")
@AllArgsConstructor
public class RaceController {

    private final RaceService raceService;
    private final AuthUser authUser;

    @GetMapping
    public List<Race> userRaces() {
        return raceService.findAllUserRaces(authUser);
    }

    @GetMapping("/{id}")
    public Race userRace(final @PathVariable Long id) {
        return raceService.findUserRaceById(authUser, id);
    }

    @GetMapping("/report")
    public List<RaceReport> weekReports() {
        return raceService.getWeekReports(authUser);
    }

    @DeleteMapping("/{id}")
    public void deleteRace(final @PathVariable Long id) {
        raceService.delete(authUser, id);
    }

    @PutMapping("/{id}")
    public void updateRace(final @PathVariable Long id, @RequestBody final Race race) {
        race.setId(id);
        raceService.save(authUser, race);
    }

    @PostMapping
    public Race createRace(@RequestBody final Race race) {
        race.setId(null);
        raceService.save(authUser, race);
        return race;
    }
}
