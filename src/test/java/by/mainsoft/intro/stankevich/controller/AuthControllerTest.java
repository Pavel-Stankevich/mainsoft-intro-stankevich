package by.mainsoft.intro.stankevich.controller;

import by.mainsoft.intro.stankevich.model.User;
import by.mainsoft.intro.stankevich.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static User user;

    @BeforeAll
    static void init() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password12345678");
    }

    @Test
    @Order(1)
    void register() {
        final AuthController.ApiResponse response = restTemplate.postForObject(
                "http://localhost:" + port + "/register", user, AuthController.ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    @Order(2)
    void registerWithExistingUsername() {
        final AuthController.ApiResponse response = restTemplate.postForObject(
                "http://localhost:" + port + "/register", user, AuthController.ApiResponse.class);
        assertFalse(response.isSuccess());
    }

    @Test
    @Order(3)
    void login() {
        final AuthController.JwtAuthenticationResponse response = restTemplate.postForObject(
                "http://localhost:" + port + "/login", user, AuthController.JwtAuthenticationResponse.class);
        final String accessToken = response.getAccessToken();
        assertTrue(jwtTokenProvider.validateToken(accessToken));
        assertEquals(user.getUsername(), jwtTokenProvider.getUsernameFromJWT(response.getAccessToken()));
    }

}
