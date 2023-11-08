package kdt.dev.ecommerce.user.fixture;

import org.springframework.test.util.ReflectionTestUtils;

import kdt.dev.ecommerce.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFixture {

	private static final String OAUTH_ID = "oauth_id";
	private static final String NICKNAME = "nickname";

	public static User getUser(Long id) {
		User user = User.of(OAUTH_ID, NICKNAME);
		ReflectionTestUtils.setField(user, "id", id);
		return user;
	}

	public static User getUser() {
		return User.of(OAUTH_ID, NICKNAME);
	}
}
