package by.mainsoft.intro.stankevich.controller;

import by.mainsoft.intro.stankevich.model.User;
import by.mainsoft.intro.stankevich.security.JwtTokenProvider;
import by.mainsoft.intro.stankevich.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthController {

    private static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
    private static final String USERNAME_IS_ALREADY_TAKEN = "Username is already taken!";
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(final @Valid @RequestBody User user) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtTokenProvider.generateToken(authentication)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(final @Valid @RequestBody User user) {
        if (userService.isUsernameAlreadyExists(user.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, USERNAME_IS_ALREADY_TAKEN), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.ok(new ApiResponse(true, USER_REGISTERED_SUCCESSFULLY));
    }

    @NoArgsConstructor
    @Data
    public static class JwtAuthenticationResponse {
        private String accessToken;
        private String tokenType;

        JwtAuthenticationResponse(final String accessToken) {
            this.accessToken = accessToken;
            this.tokenType = "Bearer";
        }
    }

    @AllArgsConstructor
    @Data
    public static class ApiResponse {
        private boolean success;
        private String message;
    }
}
