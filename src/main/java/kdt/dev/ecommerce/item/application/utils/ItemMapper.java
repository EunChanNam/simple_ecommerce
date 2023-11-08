package kdt.dev.ecommerce.item.application.utils;

import java.util.List;

import kdt.dev.ecommerce.item.application.usecase.GetItemInfoUseCase.ItemDetailInfo;
import kdt.dev.ecommerce.item.application.usecase.GetItemInfoUseCase.ItemInfo;
import kdt.dev.ecommerce.item.application.usecase.GetItemInfoUseCase.ItemInfoResponse;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {

	public static ItemInfoResponse toItemInfoResponse(List<ItemDetail> itemDetails) {
		List<ItemInfo> itemInfos = itemDetails.stream()
			.map(itemDetail -> {
				ItemDetailInfo itemDetailInfo = new ItemDetailInfo(
					itemDetail.getColor(),
					itemDetail.getSize(),
					itemDetail.getCustomOption(),
					itemDetail.getChangeAmount(),
					itemDetail.getStock()
				);
				return new ItemInfo(itemDetail.getItemName(), itemDetailInfo);
			}).toList();

		return new ItemInfoResponse(itemInfos);
	}
}
