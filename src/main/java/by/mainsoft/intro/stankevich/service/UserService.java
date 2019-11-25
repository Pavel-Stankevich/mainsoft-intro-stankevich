package by.mainsoft.intro.stankevich.service;

import by.mainsoft.intro.stankevich.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUserByUsername(final String username);

    void save(final User user);

    boolean isUsernameAlreadyExists(final String username);
}
