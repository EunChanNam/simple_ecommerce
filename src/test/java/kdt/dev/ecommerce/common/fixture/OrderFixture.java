package kdt.dev.ecommerce.common.fixture;

import kdt.dev.ecommerce.order.domain.entity.Order;
import kdt.dev.ecommerce.user.fixture.UserFixture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderFixture {

	public static Order getOrder(int orderPrice) {
		return new Order(UserFixture.getUser(), orderPrice);
	}
}
