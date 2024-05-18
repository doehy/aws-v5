package site.metacoding.awsv5.user.repository;

import site.metacoding.awsv5.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByUserIdByProviderAndOauthId(String provider, String oauthId);
}
