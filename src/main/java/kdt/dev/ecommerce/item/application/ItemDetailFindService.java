package kdt.dev.ecommerce.item.application;

import org.springframework.stereotype.Service;

import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.item.exception.ItemErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemDetailFindService {

	private final ItemDetailRepository itemDetailRepository;

	public ItemDetail getItemDetailWithItemById(Long id) {
		return itemDetailRepository.findWithItemById(id)
			.orElseThrow(() -> new CommerceException(ItemErrorCode.NOT_FOUND));
	}
}
