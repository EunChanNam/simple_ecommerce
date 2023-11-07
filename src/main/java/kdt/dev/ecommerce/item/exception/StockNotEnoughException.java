package kdt.dev.ecommerce.item.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StockNotEnoughException extends RuntimeException {

	private final String itemName;
	private final String color;
	private final String size;
	private final String customOption;
}
