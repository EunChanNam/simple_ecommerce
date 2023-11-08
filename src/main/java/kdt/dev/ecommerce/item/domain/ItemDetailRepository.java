package kdt.dev.ecommerce.item.domain;

import java.util.Optional;

import kdt.dev.ecommerce.item.domain.entity.ItemDetail;

public interface ItemDetailRepository {

	Optional<ItemDetail> findWithItemById(Long id);
}
