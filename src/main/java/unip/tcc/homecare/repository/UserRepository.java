package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.tcc.homecare.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


}
