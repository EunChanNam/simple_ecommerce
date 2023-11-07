package kdt.dev.ecommerce.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kdt.dev.ecommerce.user.domain.User;
import kdt.dev.ecommerce.user.domain.UserRepository;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {

	Optional<User> findByOauthId(String oauthId);
}
