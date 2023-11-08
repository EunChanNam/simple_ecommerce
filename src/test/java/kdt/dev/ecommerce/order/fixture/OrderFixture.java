package kdt.dev.ecommerce.order.fixture;

import java.util.List;

import kdt.dev.ecommerce.common.fixture.ItemDetailFixture;
import kdt.dev.ecommerce.common.fixture.ProductFixture;
import kdt.dev.ecommerce.order.domain.entity.Order;
import kdt.dev.ecommerce.user.fixture.UserFixture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderFixture {

	public static Order getOrder() {
		return new Order(
			UserFixture.getUser(),
			ProductFixture.getProduct(),
			3,
			List.of(ItemDetailFixture.getItemDetail())
		);
	}
}
