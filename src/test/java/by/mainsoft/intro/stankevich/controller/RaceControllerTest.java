package by.mainsoft.intro.stankevich.controller;

import by.mainsoft.intro.stankevich.model.Race;
import by.mainsoft.intro.stankevich.model.RaceReport;
import by.mainsoft.intro.stankevich.model.User;
import by.mainsoft.intro.stankevich.security.JwtTokenProvider;
import by.mainsoft.intro.stankevich.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RaceControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private HttpHeaders httpHeadersWithAuth;
    private User user;
    private static List<Race> races;

    @BeforeAll
    static void init() {
        races = Arrays.asList(
                new Race(
                        BigDecimal.valueOf(15.208).setScale(3),
                        LocalDateTime.of(2019, 11, 23, 12, 0, 5),
                        LocalTime.of(1, 0, 1)),
                new Race(
                        BigDecimal.valueOf(1.5).setScale(3),
                        LocalDateTime.of(2019, 11, 24, 13, 34, 59),
                        LocalTime.of(0, 4, 23)),
                new Race(
                        BigDecimal.valueOf(5.583).setScale(3),
                        LocalDateTime.of(2019, 11, 24, 15, 0, 2),
                        LocalTime.of(0, 19, 58)),
                new Race(
                        BigDecimal.valueOf(6.5).setScale(3),
                        LocalDateTime.of(2019, 11, 24, 13, 34, 59),
                        LocalTime.of(0, 4, 27)),
                new Race(
                        BigDecimal.valueOf(16.119).setScale(3),
                        LocalDateTime.of(2019, 11, 25, 6, 24, 54),
                        LocalTime.of(1, 0, 23)),
                new Race(
                        BigDecimal.valueOf(5.205).setScale(3),
                        LocalDateTime.of(2019, 11, 25, 19, 48, 37),
                        LocalTime.of(0, 20, 0))
        );
    }

    @BeforeEach
    void initBeforeTest() {
        final String username = "testUserRace";
        if (userService.isUsernameAlreadyExists(username)) {
            user = userService.getUserByUsername(username);
        } else {
            user = new User();
            user.setUsername(username);
            user.setPassword("password12345678");
            userService.save(user);
        }
        httpHeadersWithAuth = new HttpHeaders();
        httpHeadersWithAuth.setBearerAuth(jwtTokenProvider.generateToken(user.getUsername()));
    }

    @Test
    @Order(1)
    void createRace() {
        races.forEach(race -> {
            final ResponseEntity<Race> response = restTemplate.exchange(
                    "http://localhost:" + port + "/races/",
                    HttpMethod.POST,
                    new HttpEntity<>(race, httpHeadersWithAuth),
                    Race.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            final Long raceId = response.getBody().getId();
            assertNotNull(raceId);
            race.setId(raceId);
        });
    }

    @Test
    @Order(2)
    void userRaces() {
        final ResponseEntity<List<Race>> response = restTemplate.exchange(
                "http://localhost:" + port + "/races/",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeadersWithAuth),
                new ParameterizedTypeReference<List<Race>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        final List<Race> racesFromResponse = response.getBody();
        assertEquals(races, racesFromResponse);
    }


    @Test
    @Order(3)
    void weekReports() {
        final ResponseEntity<List<RaceReport>> response = restTemplate.exchange(
                "http://localhost:" + port + "/races/report",
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeadersWithAuth),
                new ParameterizedTypeReference<List<RaceReport>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @Order(4)
    void userRace() {
        races.forEach(race -> {
            final ResponseEntity<Race> response = restTemplate.exchange(
                    "http://localhost:" + port + "/races/{id}",
                    HttpMethod.GET,
                    new HttpEntity<>(null, httpHeadersWithAuth),
                    Race.class,
                    race.getId());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.hasBody());
            assertEquals(race, response.getBody());
        });
    }

    @Test
    @Order(5)
    void updateRace() {
        races.forEach(race -> {
            race.setDistance(race.getDistance().add(BigDecimal.valueOf(0.1)));
            final ResponseEntity<Void> putResponse = restTemplate.exchange(
                    "http://localhost:" + port + "/races/{id}",
                    HttpMethod.PUT,
                    new HttpEntity<>(race, httpHeadersWithAuth),
                    Void.class,
                    race.getId());
            assertEquals(HttpStatus.OK, putResponse.getStatusCode());
            assertFalse(putResponse.hasBody());
            final ResponseEntity<Race> getResponse = restTemplate.exchange(
                    "http://localhost:" + port + "/races/{id}",
                    HttpMethod.GET,
                    new HttpEntity<>(null, httpHeadersWithAuth),
                    Race.class,
                    race.getId());
            assertEquals(HttpStatus.OK, getResponse.getStatusCode());
            assertTrue(getResponse.hasBody());
            assertEquals(race, getResponse.getBody());
        });
    }

    @Test
    @Order(6)
    void deleteRace() {
        races.forEach(race -> {
            final ResponseEntity<Void> responseForPutMethod = restTemplate.exchange(
                    "http://localhost:" + port + "/races/{id}",
                    HttpMethod.DELETE,
                    new HttpEntity<>(null, httpHeadersWithAuth),
                    Void.class,
                    race.getId());
            assertEquals(HttpStatus.OK, responseForPutMethod.getStatusCode());
            assertFalse(responseForPutMethod.hasBody());
            final ResponseEntity<Void> responseForGetMethod = restTemplate.exchange(
                    "http://localhost:" + port + "/races/{id}",
                    HttpMethod.GET,
                    new HttpEntity<>(null, httpHeadersWithAuth),
                    Void.class,
                    race.getId());
            assertEquals(HttpStatus.NOT_FOUND, responseForGetMethod.getStatusCode());
        });
    }
}
