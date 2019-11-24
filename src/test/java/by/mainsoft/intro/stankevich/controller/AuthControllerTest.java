package by.mainsoft.intro.stankevich.controller;

import by.mainsoft.intro.stankevich.model.User;
import by.mainsoft.intro.stankevich.security.JwtTokenProvider;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

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
        AuthController.ApiResponse response = restTemplate.postForObject(
                "http://localhost:" + port + "/register", user, AuthController.ApiResponse.class);
        assertTrue(response.isSuccess());
    }

    @Test
    @Order(2)
    void registerWithExistingUsername() {
        AuthController.ApiResponse response = restTemplate.postForObject(
                "http://localhost:" + port + "/register", user, AuthController.ApiResponse.class);
        assertFalse(response.isSuccess());
    }

    @Test
    @Order(3)
    void login() {
            AuthController.JwtAuthenticationResponse response = restTemplate.postForObject(
                    "http://localhost:" + port + "/login", user, AuthController.JwtAuthenticationResponse.class);
            assertTrue(jwtTokenProvider.validateToken(response.getAccessToken()));
    }

}