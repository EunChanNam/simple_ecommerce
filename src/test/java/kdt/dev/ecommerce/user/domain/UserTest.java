package kdt.dev.ecommerce.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[User 테스트]")
class UserTest {

	@Test
	@DisplayName("[User 를 생성한다]")
	void of() {
		//given
		String oauthId = "oauthId";
		String nickname = "nickname";

		//when
		User actual = User.of(oauthId, nickname);

		//then
		assertAll(
			() -> assertThat(actual.getOauthId()).isEqualTo(oauthId),
			() -> assertThat(actual.getNickname()).isEqualTo(nickname)
		);
	}
}