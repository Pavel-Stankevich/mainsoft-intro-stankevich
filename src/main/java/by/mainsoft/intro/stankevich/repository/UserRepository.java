package by.mainsoft.intro.stankevich.repository;

import by.mainsoft.intro.stankevich.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(final String username);

    boolean existsByUsername(final String username);
}
