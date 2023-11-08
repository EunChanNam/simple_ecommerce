package kdt.dev.ecommerce.item.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;

public interface ItemDetailJpaRepository extends ItemDetailRepository, JpaRepository<ItemDetail, Long> {

	@Override
	@EntityGraph(attributePaths = "item")
	Optional<ItemDetail> findWithItemById(Long id);

	@EntityGraph(attributePaths = "item")
	List<ItemDetail> findWithByIdIn(List<Long> ids);

	@Query("select itemDetail from ItemDetail itemDetail "
		   + "join fetch itemDetail.item i "
		   + "join ProductItem pi on i.id = pi.item.id "
		   + "join Product p on pi.product.id = p.id "
		   + "where p.id = :productId")
	List<ItemDetail> findByProductId(@Param("productId") Long productId);
}
