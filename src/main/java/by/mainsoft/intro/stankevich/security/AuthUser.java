package by.mainsoft.intro.stankevich.security;

import by.mainsoft.intro.stankevich.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthUser extends User {

    void setAuthUser(final User user) {
        super.setId(user.getId());
        super.setUsername(user.getUsername());
        super.setPassword(user.getPassword());
    }
}
