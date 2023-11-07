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
import kdt.dev.ecommerce.global.entity.BaseEntity;
import kdt.dev.ecommerce.item.domain.entity.Option;
import kdt.dev.ecommerce.product.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "order_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Order order;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Product product;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "option_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Option option;

	private int quantity;

	public OrderProduct(
		Order order,
		Product product,
		Option option,
		int quantity
	) {
		this.order = order;
		this.product = product;
		this.option = option;
		this.quantity = quantity;
	}
}
