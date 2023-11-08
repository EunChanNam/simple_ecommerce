package kdt.dev.ecommerce.product.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import kdt.dev.ecommerce.common.fixture.ProductFixture;
import kdt.dev.ecommerce.common.support.MockTestSupport;
import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.product.domain.ProductRepository;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.product.exception.ProductErrorCode;

@DisplayName("[ProductFindService 테스트]")
class ProductFindServiceTest extends MockTestSupport {

	@InjectMocks
	private ProductFindService productFindService;
	@Mock
	private ProductRepository productRepository;

	@Nested
	@DisplayName("[ID 로 Promotion 정보와 함께 Product 를 조회한다]")
	class getProductWithPromotionById {

		@Test
		@DisplayName("[성공적으로 조회한다]")
		void success() {
			//given
			Product product = ProductFixture.getProduct(20, 10000);
			given(productRepository.findWithPromotionById(1L))
				.willReturn(Optional.of(product));

			//when
			Product actual = productFindService.getProductWithPromotionById(1L);

			//then
			assertThat(actual).isEqualTo(product);
		}

		@Test
		@DisplayName("[id 에 해당하는 Product 가 존재하지 않아 실패한다]")
		void fail() {
			//given
			given(productRepository.findWithPromotionById(1L))
				.willReturn(Optional.empty());

			//when
			ThrowableAssert.ThrowingCallable when = () -> productFindService.getProductWithPromotionById(1L);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(ProductErrorCode.NOT_FOUND.getMessage());
		}
	}
}