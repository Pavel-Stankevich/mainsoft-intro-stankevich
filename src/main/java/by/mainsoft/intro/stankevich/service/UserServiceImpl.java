package by.mainsoft.intro.stankevich.service;

import by.mainsoft.intro.stankevich.model.User;
import by.mainsoft.intro.stankevich.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User with username %s not found.";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(optionalUser.get().getPassword());
            builder.roles("USER");
            return builder.build();
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        }
    }

    @Override
    public void save(final User user) {
        User user0 = userRepository.save(user);
        user.setId(user0.getId());
    }

    @Override
    public boolean isUsernameAlreadyExists(final String username) {
        return userRepository.existsByUsername(username);
    }
}
