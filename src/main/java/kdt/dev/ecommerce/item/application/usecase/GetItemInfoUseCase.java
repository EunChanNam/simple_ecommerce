package kdt.dev.ecommerce.item.application.usecase;

import java.util.List;

public interface GetItemInfoUseCase {

	ItemInfoResponse getItemInfo(Long productId);

	record ItemInfoResponse(
		List<ItemInfo> itemInfos
	) {
	}

	record ItemInfo(
		String itemName,
		ItemDetailInfo itemDetailInfo
	) {
	}

	record ItemDetailInfo(
		Long itemDetailId,
		String color,
		String size,
		String customOption,
		int changeAmount,
		int stock
	) {
	}
}
