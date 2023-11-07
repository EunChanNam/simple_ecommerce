package kdt.dev.ecommerce.common.fixture;

import kdt.dev.ecommerce.item.domain.dto.ItemDetailDto;
import kdt.dev.ecommerce.item.domain.entity.Item;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemDetailFixture {

	public static ItemDetail getItemDetail(int itemPrice, int changeAmount, int stock) {
		return new ItemDetail(new ItemDetailDto(
			new Item("item", itemPrice),
			"red",
			"XL",
			null,
			changeAmount,
			stock
		));
	}
}
