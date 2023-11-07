package kdt.dev.ecommerce.item.domain.dto;

import kdt.dev.ecommerce.item.domain.entity.Item;

public record OptionDto(
	Item item,
	String color,
	String size,
	String customOption,
	String changeAmount,
	int stock
) {
}
