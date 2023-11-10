package kdt.dev.ecommerce.global.exception.dto;


public record StockNotEnoughResponse(
	String message,
	String itemName,
	String color,
	String size,
	String customOption
) {
}
