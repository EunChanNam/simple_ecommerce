package kdt.dev.ecommerce.user.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import kdt.dev.ecommerce.common.support.MockTestSupport;
import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.user.domain.User;
import kdt.dev.ecommerce.user.domain.UserRepository;
import kdt.dev.ecommerce.user.exception.UserErrorCode;
import kdt.dev.ecommerce.user.fixture.UserFixture;

@DisplayName("[UserFindService 테스트]")
class UserFindServiceTest extends MockTestSupport {

	@InjectMocks
	private UserFindService userFindService;
	@Mock
	private UserRepository userRepository;

	@Nested
	@DisplayName("[ID 로 User 를 조회한다]")
	class getProductWithPromotionById {

		@Test
		@DisplayName("[성공적으로 조회한다]")
		void success() {
			//given
			User user = UserFixture.getUser();
			given(userRepository.findById(1L))
				.willReturn(Optional.of(user));

			//when
			User actual = userFindService.getById(1L);

			//then
			assertThat(actual).isEqualTo(user);
		}

		@Test
		@DisplayName("[ID 에 해당하는 User 가 존재하지 않아 실패한다]")
		void fail() {
			//given
			given(userRepository.findById(1L))
				.willReturn(Optional.empty());

			//when
			ThrowingCallable when = () -> userFindService.getById(1L);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(UserErrorCode.NOT_FOUND.getMessage());
		}
	}
}