package kdt.dev.ecommerce.user.domain;

import java.util.Optional;

public interface UserRepository {

	User save(User user);

	Optional<User> findById(Long id);

	Optional<User> findByOauthId(String oauthId);
}
