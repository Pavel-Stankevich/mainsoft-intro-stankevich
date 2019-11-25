package by.mainsoft.intro.stankevich.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RaceNotFoundException extends RuntimeException {

    public RaceNotFoundException(final Long raceId) {
        super(String.format("Could not find race with id %d", raceId));
    }
}
