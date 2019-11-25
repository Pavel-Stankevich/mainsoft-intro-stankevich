package by.mainsoft.intro.stankevich.converter;

import by.mainsoft.intro.stankevich.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {

    @Override
    public UserDetails convert(final User source) {
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(source.getUsername());
        builder.password(source.getPassword());
        builder.roles("USER");
        return builder.build();
    }
}
