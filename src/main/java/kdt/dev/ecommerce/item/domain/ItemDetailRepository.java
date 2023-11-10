package kdt.dev.ecommerce.item.domain;

import java.util.List;
import java.util.Optional;

import kdt.dev.ecommerce.item.domain.entity.ItemDetail;

public interface ItemDetailRepository {

	Optional<ItemDetail> findWithItemById(Long id);

	List<ItemDetail> findWithLockByIdIn(List<Long> ids);

	List<ItemDetail> findByProductId(Long productId);
}
