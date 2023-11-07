package kdt.dev.ecommerce.item.domain.dto;

import kdt.dev.ecommerce.item.domain.entity.Item;

public record ItemDetailDto(
	Item item,
	String color,
	String size,
	String customOption,
	int changeAmount,
	int stock
) {
}
