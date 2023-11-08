package kdt.dev.ecommerce.order.domain.entity;

import static jakarta.persistence.ConstraintMode.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kdt.dev.ecommerce.global.entity.BaseEntity;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
	private Product product;

	private int quantity;

	private int totalPrice;

	@Embedded
	private OrderDetails orderDetails;

	public Order(
		User user,
		Product product,
		int quantity,
		List<ItemDetail> itemDetails
	) {
		this.user = user;
		this.product = product;
		this.quantity = quantity;
		this.orderDetails = new OrderDetails(this, itemDetails, quantity);
		this.totalPrice = calculatePrice();
	}

	private int calculatePrice() {
		int discountedPrice = product.getDiscountedPrice() * quantity;
		int additionalPrice = orderDetails.getAdditionalPrice() * quantity;
		return discountedPrice + additionalPrice;
	}

	public String getProductName() {
		return product.getProductName();
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails.getOrderDetails();
	}
}
