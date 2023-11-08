package kdt.dev.ecommerce.item.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kdt.dev.ecommerce.item.application.usecase.GetItemInfoUseCase;
import kdt.dev.ecommerce.item.application.utils.ItemMapper;
import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService implements GetItemInfoUseCase {

	private final ItemDetailRepository itemDetailRepository;

	@Override
	public ItemInfoResponse getItemInfo(Long productId) {
		List<ItemDetail> itemDetails = itemDetailRepository.findByProductId(productId);
		return ItemMapper.toItemInfoResponse(itemDetails);
	}
}
