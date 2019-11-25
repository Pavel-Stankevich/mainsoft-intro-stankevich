package by.mainsoft.intro.stankevich.service;

import by.mainsoft.intro.stankevich.model.User;
import by.mainsoft.intro.stankevich.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User with username %s not found.";

    private final UserRepository userRepository;
    private final Converter<User, UserDetails> userToUserDetailsConverter;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            return userToUserDetailsConverter.convert(optionalUser.get());
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        }
    }

    @Transactional
    @Override
    public User getUserByUsername(final String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        }
    }

    @Transactional
    @Override
    public void save(final User user) {
        User user0 = userRepository.save(user);
        user.setId(user0.getId());
    }

    @Transactional
    @Override
    public boolean isUsernameAlreadyExists(final String username) {
        return userRepository.existsByUsername(username);
    }
}
