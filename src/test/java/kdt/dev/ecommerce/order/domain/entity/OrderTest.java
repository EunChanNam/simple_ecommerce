package kdt.dev.ecommerce.order.domain.entity;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kdt.dev.ecommerce.common.fixture.ItemDetailFixture;
import kdt.dev.ecommerce.common.fixture.ProductFixture;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.item.exception.StockNotEnoughException;
import kdt.dev.ecommerce.product.domain.entity.Product;

@DisplayName("[Order 테스트]")
class OrderTest {

	@Nested
	@DisplayName("[Order 를 생성한다]")
	class of {

		@Test
		@DisplayName("[성공적으로 생성한다]")
		void success() {
			//given
			int discountAmount = 10;
			int quantity = 10;
			int itemPrice = 10000;
			int changeAmount = 1000;
			int stock = 100;

			Product product = ProductFixture.getProduct(discountAmount);
			ItemDetail itemDetail = ItemDetailFixture.getItemDetail(itemPrice, changeAmount, stock);

			//when
			Order actual = Order.of(
				product,
				itemDetail,
				quantity
			);

			//then
			assertThat(actual.getPrice()).isEqualTo(100000);
			assertThat(itemDetail.getStock()).isEqualTo(90);
		}

		@Test
		@DisplayName("[ItemDetail 의 재고가 부족해서 실패한다]")
		void fail() {
			//given
			int discountAmount = 10;
			int quantity = 10;
			int itemPrice = 10000;
			int changeAmount = 1000;
			int stock = 5;

			Product product = ProductFixture.getProduct(discountAmount);
			ItemDetail itemDetail = ItemDetailFixture.getItemDetail(itemPrice, changeAmount, stock);

			//when
			ThrowingCallable when = () ->
				Order.of(
					product,
					itemDetail,
					quantity
				);

			//then
			assertThatThrownBy(when).isInstanceOf(StockNotEnoughException.class);
		}
	}
}