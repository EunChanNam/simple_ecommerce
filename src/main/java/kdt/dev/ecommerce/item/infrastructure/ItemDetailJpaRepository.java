package kdt.dev.ecommerce.item.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;

public interface ItemDetailJpaRepository extends ItemDetailRepository, JpaRepository<ItemDetail, Long> {

	@Override
	@EntityGraph(attributePaths = "item")
	Optional<ItemDetail> findWithItemById(Long id);

	@EntityGraph(attributePaths = "item")
	List<ItemDetail> findWithByIdIn(List<Long> ids);
}
