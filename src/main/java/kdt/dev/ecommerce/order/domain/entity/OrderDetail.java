package kdt.dev.ecommerce.order.domain.entity;

import static jakarta.persistence.ConstraintMode.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "order_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Order order;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "item_detail_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private ItemDetail itemDetail;

	public OrderDetail(
		Order order,
		ItemDetail itemDetail
	) {
		this.order = order;
		this.itemDetail = itemDetail;
	}

	public int getChangeAmount() {
		return itemDetail.getChangeAmount();
	}
}
