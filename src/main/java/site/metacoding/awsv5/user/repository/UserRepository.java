package site.metacoding.awsv5.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.metacoding.awsv5.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUserToken(String userToken);
}
